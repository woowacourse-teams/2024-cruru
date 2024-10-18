package com.cruru.auth.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.auth.domain.RefreshToken;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("리프레쉬 토큰 레포지터리 테스트")
class RefreshTokenRepositoryTest extends RepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        refreshTokenRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();

        member = memberRepository.save(MemberFixture.DOBBY);
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 토큰을 저장하면, 해당 ID의 토큰은 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        String token = "token";
        RefreshToken saved = refreshTokenRepository.save(new RefreshToken(token, member));

        //when
        String changedToken = "changedToken";
        RefreshToken refreshToken = new RefreshToken(saved.getId(), changedToken, null);
        refreshTokenRepository.save(refreshToken);

        //then
        RefreshToken foundToken = refreshTokenRepository.findById(saved.getId()).get();
        assertThat(foundToken.getToken()).isEqualTo(changedToken);
    }

    @DisplayName("ID가 없는 사용자를 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        Member member1 = memberRepository.save(MemberFixture.RUSH);
        RefreshToken refreshToken1 = new RefreshToken("token", member);
        RefreshToken refreshToken2 = new RefreshToken("token2", member1);

        //when
        RefreshToken savedrefreshToken1 = refreshTokenRepository.save(refreshToken1);
        RefreshToken savedrefreshToken2 = refreshTokenRepository.save(refreshToken2);

        //then
        assertThat(savedrefreshToken1.getId() + 1).isEqualTo(savedrefreshToken2.getId());
    }
}
