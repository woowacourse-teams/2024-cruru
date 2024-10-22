package com.cruru.member.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import com.cruru.email.exception.NotVerifiedEmailException;
import com.cruru.email.service.EmailRedisClient;
import com.cruru.member.controller.request.MemberCreateRequest;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ServiceTest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("회원 파사드 서비스 테스트")
class MemberFacadeTest extends ServiceTest {

    @Autowired
    private MemberFacade memberFacade;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private EmailRedisClient emailRedisClient;

    @DisplayName("사용자를 생성하면 ID를 반환한다.")
    @Test
    void create() {
        // given
        String clubName = "크루루";
        String email = "new@mail.com";
        String password = "newPassword214!";
        String phone = "01012341234";
        MemberCreateRequest request = new MemberCreateRequest(clubName, email, password, phone);
        doNothing().when(emailRedisClient).verifyEmail(email);

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

    @DisplayName("인증되지 않은 사용자로 사용자를 생성하면, 예외가 발생한다.")
    @Test
    void create_notVerifiedEmail() {
        // given
        String clubName = "크루루";
        String email = "new@mail.com";
        String password = "newPassword214!";
        String phone = "01012341234";
        MemberCreateRequest request = new MemberCreateRequest(clubName, email, password, phone);
        doThrow(NotVerifiedEmailException.class).when(emailRedisClient).verifyEmail(email);

        // when&then
        assertThatThrownBy(() -> memberFacade.create(request))
                .isInstanceOf(NotVerifiedEmailException.class);
    }
}
