package com.cruru.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 사용자를 저장하면, 해당 ID의 사용자는 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Member member = new Member("email", "", "");
        Member saved = memberRepository.save(member);

        //when
        Member updateMember = new Member(saved.getId(), "email", "newPassword", "newPhoneNumber");
        memberRepository.save(updateMember);

        //then
        Member findMember = memberRepository.findById(saved.getId()).get();
        assertThat(findMember.getPassword()).isEqualTo("newPassword");
        assertThat(findMember.getPhone()).isEqualTo("newPhoneNumber");
    }

    @DisplayName("ID가 없는 사용자를 저장하면, 순차적으로 ID가 부여하여 저장된다.")
    @Test
    void saveNoId() {
        //given
        Member member1 = new Member("email", "", "");
        Member member2 = new Member("email", "", "");

        //when
        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);

        //then
        assertThat(savedMember1.getId() + 1).isEqualTo(savedMember2.getId());
    }
}
