package com.cruru.applicant.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.exception.badrequest.ApplicantNameBlankException;
import com.cruru.applicant.exception.badrequest.ApplicantNameCharacterException;
import com.cruru.applicant.exception.badrequest.ApplicantNameLengthException;
import com.cruru.util.fixture.ApplicantFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("지원서 도메인 테스트")
class ApplicantTest {

    @DisplayName("지원자 이름은 한글, 영어, 공백, '-'를 허용한다.")
    @ValueSource(strings = {"도비", "dobby", "김 도비", "kim-dobby"})
    @ParameterizedTest
    void validApplicantName(String name) {
        // given&when&then
        assertThatCode(() -> new Applicant(name, "mail@mail.com", "01012341234", null)).doesNotThrowAnyException();
    }

    @DisplayName("지원자 이름이 비어있으면 예외가 발생한다.")
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void ApplicantNameBlank(String name) {
        // given&when&then
        assertThatThrownBy(() -> new Applicant(name, "mail@mail.com", "01012341234", null))
                .isInstanceOf(ApplicantNameBlankException.class);
    }

    @DisplayName("지원자 이름이 32자 초과시 예외가 발생한다.")
    @Test
    void invalidApplicantNameLength() {
        // given
        String name = "ThisStringLengthIsThirtyThreeAbcd";

        // when&then
        assertThatThrownBy(() -> new Applicant(name, "mail@mail.com", "01012341234", null))
                .isInstanceOf(ApplicantNameLengthException.class);
    }

    @DisplayName("지원자 이름에 허용되지 않은 글자가 들어가면 예외가 발생한다.")
    @ValueSource(strings = {"invalidCharacter!", "invalidCharacter~"})
    @ParameterizedTest
    void invalidApplicantNameCharacter(String name) {
        // given&when&then
        assertThatThrownBy(() -> new Applicant(name, "mail@mail.com", "01012341234", null))
                .isInstanceOf(ApplicantNameCharacterException.class);
    }

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

    @DisplayName("지원자를 불합격시킨다.")
    @Test
    void reject() {
        // given
        Applicant applicant = ApplicantFixture.pendingDobby();

        // when
        applicant.reject();

        // then
        assertThat(applicant.isRejected()).isTrue();
    }

    @DisplayName("지원자의 불합격을 취소한다.")
    @Test
    void unreject() {
        // given
        Applicant applicant = ApplicantFixture.rejectedRush();

        // when
        applicant.unreject();

        // then
        assertThat(applicant.isNotRejected()).isTrue();
    }
}
