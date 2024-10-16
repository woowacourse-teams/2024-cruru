package com.cruru.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.auth.domain.AccessToken;
import com.cruru.auth.domain.RefreshToken;
import com.cruru.auth.domain.Token;
import com.cruru.auth.domain.repository.RefreshTokenRepository;
import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AuthService 테스트")
class AuthServiceTest extends ServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(MemberFixture.DOBBY);
    }

    @DisplayName("AccessToken을 생성한다.")
    @Test
    void createAccessToken() {
        // given&when
        Token accessToken = authService.createAccessToken(member);

        // then
        assertAll(
                () -> assertThat(accessToken).isInstanceOf(AccessToken.class),
                () -> assertThat(accessToken.getToken()).isNotEmpty()
        );
    }

    @DisplayName("토큰이 유효한지 확인한다.")
    @Test
    void isTokenValid() {
        // given
        Token accessToken = authService.createAccessToken(member);

        // when
        boolean isValid = authService.isTokenValid(accessToken.getToken());

        // then
        assertThat(isValid).isTrue();
    }

    @DisplayName("토큰이 유효하지 않으면 false를 반환한다.")
    @Test
    void isTokenInvalid() {
        // given
        String invalidToken = "invalidToken";

        // when
        boolean isValid = authService.isTokenValid(invalidToken);

        // then
        assertThat(isValid).isFalse();
    }

    @DisplayName("토큰에서 이메일을 추출한다.")
    @Test
    void extractEmail() {
        // given
        Token accessToken = authService.createAccessToken(member);

        // when
        String email = authService.extractEmail(accessToken.getToken());

        // then
        assertThat(email).isEqualTo(member.getEmail());
    }

    @DisplayName("토큰에서 역할을 추출한다.")
    @Test
    void extractRole() {
        // given
        Token accessToken = authService.createAccessToken(member);

        // when
        String role = authService.extractMemberRole(accessToken.getToken());

        // then
        assertThat(role).isEqualTo(member.getRole().name());
    }

    @DisplayName("잘못된 토큰에서 이메일 추출 시 예외를 발생시킨다.")
    @Test
    void extractClaimThrowsIllegalTokenException() {
        // given
        String invalidToken = "invalidToken";

        // when & then
        assertThatThrownBy(() -> authService.extractEmail(invalidToken))
                .isInstanceOf(IllegalTokenException.class);
    }

    @DisplayName("비밀번호가 일치하지 않으면 true를 반환한다.")
    @Test
    void isNotVerifiedPassword() {
        // given
        String rawPassword = "plainPassword";
        String encodedPassword = "encodedPassword123";

        // when
        boolean result = authService.isNotVerifiedPassword(rawPassword, encodedPassword);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("토큰 rotate에 성공한다.")
    @Test
    void rotate() throws InterruptedException {
        // given
        Token refreshToken = refreshTokenRepository.save((RefreshToken) authService.createRefreshToken(member));

        // when
        Thread.sleep(1000);
        Token rotatedRefreshToken = authService.rotate(member);

        // then
        assertThat(refreshToken.getToken()).isNotEqualTo(rotatedRefreshToken.getToken());
    }
}
