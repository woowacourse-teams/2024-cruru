package com.cruru.applicant.domain;

import static com.cruru.applicant.domain.ApplicantState.APPROVED;
import static com.cruru.applicant.domain.ApplicantState.PENDING;
import static com.cruru.applicant.domain.ApplicantState.REJECTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.util.fixture.ApplicantFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("지원서 도메인 테스트")
class ApplicantTest {

    @DisplayName("지원자 이름, 이메일, 전화번호 변경에 성공한다.")
    @Test
    void updateInfo() {
        // given
        String toChangeName = "초코칩";
        String toChangeEmail = "dev.chocochip@gmail.com";
        String toChangePhone = "01000000000";

        Applicant applicant = ApplicantFixture.pendingDobby();

        // when
        applicant.updateInfo(toChangeName, toChangeEmail, toChangePhone);

        // then
        assertAll(
                () -> assertThat(applicant.getName()).isEqualTo(toChangeName),
                () -> assertThat(applicant.getEmail()).isEqualTo(toChangeEmail),
                () -> assertThat(applicant.getPhone()).isEqualTo(toChangePhone),
                () -> assertThat(applicant.getProcess()).isNull()
        );
    }

    @DisplayName("지원자의 상태를 REJECTED로 업데이트한다.")
    @Test
    void reject() {
        // given
        Applicant applicant = ApplicantFixture.pendingDobby();

        // when
        applicant.reject();

        // then
        assertAll(
                () -> assertThat(applicant.getState()).isEqualTo(REJECTED),
                () -> assertThat(applicant.isRejected()).isTrue()
        );
    }

    @DisplayName("지원자의 상태를 PENDING로 업데이트한다.")
    @Test
    void unreject() {
        // given
        Applicant applicant = ApplicantFixture.rejectedRush();

        // when
        applicant.unreject();

        // then
        assertAll(
                () -> assertThat(applicant.getState()).isEqualTo(PENDING),
                () -> assertThat(applicant.isPending()).isTrue()
        );
    }

    @DisplayName("지원자의 상태를 APPROVE로 업데이트한다.")
    @Test
    void approve() {
        // given
        Applicant applicant = ApplicantFixture.pendingDobby();

        // when
        applicant.approve();

        // then
        assertAll(
                () -> assertThat(applicant.getState()).isEqualTo(APPROVED),
                () -> assertThat(applicant.isApproved()).isTrue()
        );
    }
}
