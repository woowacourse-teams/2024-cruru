package com.cruru.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.member.domain.Member;
import com.cruru.member.domain.MemberRole;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@DisplayName("회원 레포지토리 테스트")
class MemberRepositoryTest extends RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 사용자를 저장하면, 해당 ID의 사용자는 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Member member = MemberFixture.DOBBY;
        Member saved = memberRepository.save(member);

        //when
        Member updateMember = new Member(saved.getId(), "email", "newPassword214!", "01012341234", MemberRole.ADMIN);
        memberRepository.save(updateMember);

        //then
        Member findMember = memberRepository.findById(saved.getId()).get();
        assertThat(findMember.getPassword()).isEqualTo("newPassword214!");
        assertThat(findMember.getPhone()).isEqualTo("01012341234");
    }

    @DisplayName("ID가 없는 사용자를 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        Member member1 = MemberFixture.DOBBY;
        Member member2 = MemberFixture.RUSH;

        //when
        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);

        //then
        assertThat(savedMember1.getId() + 1).isEqualTo(savedMember2.getId());
    }

    @DisplayName("같은 email을 가진 member를 저장 시, 예외가 발생한다.")
    @Test
    void save_duplicateEmail() {
        //given
        Member member1 = MemberFixture.DOBBY;
        Member member2 = MemberFixture.DOBBY;

        //when
        memberRepository.save(member1);

        //then
        assertThatThrownBy(() -> memberRepository.save(member2)).isInstanceOf(DataIntegrityViolationException.class);
    }
}
