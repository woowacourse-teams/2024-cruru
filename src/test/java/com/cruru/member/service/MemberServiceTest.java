package com.cruru.member.service;

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

@DisplayName("사용자 서비스 테스트")
class MemberServiceTest extends ServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("사용자를 생성하면 ID를 반환한다.")
    @Test
    void create() {
        // given
        String email = "mail@mail.com";
        String password = "qwer1234";
        String phone = "01012341234";
        MemberCreateRequest request = new MemberCreateRequest(email, password, phone);

        // when
        long memberId = memberService.create(request);

        // then
        Optional<Member> member = memberRepository.findById(memberId);
        assertAll(
                () -> assertThat(member).isPresent(),
                () -> assertThat(member.get().getEmail()).isEqualTo(email),
                () -> assertThat(member.get().getPhone()).isEqualTo(phone)
        );
    }
}
