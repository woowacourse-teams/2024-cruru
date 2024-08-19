package com.cruru.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.cruru.member.controller.dto.MemberCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.member.exception.badrequest.MemberIllegalPasswordException;
import com.cruru.member.exception.badrequest.MemberPasswordLengthException;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.MemberFixture;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
        String clubName = "크루루";
        String email = "mail@mail.com";
        String password = "newPassword214!";
        String phone = "01012341234";
        MemberCreateRequest request = new MemberCreateRequest(clubName, email, password, phone);

        // when
        Member member = memberService.create(request);

        // then
        Optional<Member> actualMember = memberRepository.findById(member.getId());
        assertAll(
                () -> assertThat(actualMember).isPresent(),

                () -> assertThat(actualMember.get().getEmail()).isEqualTo(email),
                () -> assertThat(actualMember.get().getPhone()).isEqualTo(phone)
        );
    }

    @DisplayName("회원을 ID로 조회한다.")
    @Test
    void findById() {
        // given
        Member savedMember = memberRepository.save(MemberFixture.DOBBY);
        Member actualMember = memberService.findById(savedMember.getId());
        // when&then
        assertAll(
                () -> assertDoesNotThrow(() -> memberService.findById(savedMember.getId())),

                () -> assertThat(actualMember.getEmail()).isEqualTo(savedMember.getEmail()),
                () -> assertThat(actualMember.getPhone()).isEqualTo(savedMember.getPhone()),
                () -> assertThat(actualMember.getPassword()).isEqualTo(savedMember.getPassword())
        );
    }

    @DisplayName("허용되지 않는 비밀번호 길이로 Member 생성 시 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "short1!",     // 길이가 8자 미만
            "VeryLongPassword12345678901234567890!" // 길이가 32자 초과
    })
    void createMemberWithLengthPassword(String password) {
        // given
        String email = "test@example.com";
        String phone = "01012345678";
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("크루루", email, password, phone);

        // when&then
        assertThatThrownBy(() -> memberService.create(memberCreateRequest))
                .isInstanceOf(MemberPasswordLengthException.class);
    }

    @DisplayName("허용되지 않는 비밀번호 형식으로 Member 생성 시 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "NoNumber!",    // 숫자가 없음
            "NoSpecial123" // 특수문자가 없음
    })
    void createMemberWithInvalidPassword(String password) {
        // given
        String email = "test@example.com";
        String phone = "01012345678";
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("크루루", email, password, phone);

        // when&then
        assertThatThrownBy(() -> memberService.create(memberCreateRequest))
                .isInstanceOf(MemberIllegalPasswordException.class);
    }

    @DisplayName("회원을 email로 조회한다.")
    @Test
    void findByEmail() {
        // given
        Member savedMember = memberRepository.save(MemberFixture.DOBBY);
        Member actualMember = memberService.findByEmail(savedMember.getEmail());
        // when&then
        assertAll(
                () -> assertDoesNotThrow(() -> memberService.findByEmail(savedMember.getEmail())),

                () -> assertThat(actualMember.getId()).isEqualTo(savedMember.getId()),
                () -> assertThat(actualMember.getEmail()).isEqualTo(savedMember.getEmail()),
                () -> assertThat(actualMember.getPhone()).isEqualTo(savedMember.getPhone()),
                () -> assertThat(actualMember.getPassword()).isEqualTo(savedMember.getPassword())
        );
    }
}
