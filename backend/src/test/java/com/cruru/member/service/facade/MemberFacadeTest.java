package com.cruru.member.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.member.controller.dto.MemberCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ServiceTest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("회원 파사드 서비스 테스트")
class MemberFacadeTest extends ServiceTest {

    @Autowired
    private MemberFacade memberFacade;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("사용자를 생성하면 ID를 반환한다.")
    @Test
    void create() {
        // given
        String clubName = "크루루";
        String email = "mail@mail.com";
        String password = "newPassword214!";
        String phone = "01012341234";
        MemberCreateRequest request = new MemberCreateRequest(clubName, email, password, phone);

        // when
        long memberId = memberFacade.create(request);

        // then
        Optional<Member> member = memberRepository.findById(memberId);
        assertAll(
                () -> assertThat(member).isPresent(),
                () -> assertThat(member.get().getEmail()).isEqualTo(email),
                () -> assertThat(member.get().getPhone()).isEqualTo(phone)
        );
    }
}
