package com.cruru.applicant.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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

        Applicant applicant = new Applicant(null, null, null, null, false);

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
}
