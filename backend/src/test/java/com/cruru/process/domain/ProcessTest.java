package com.cruru.process.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.exception.badrequest.ProcessNameBlankException;
import com.cruru.process.exception.badrequest.ProcessNameCharacterException;
import com.cruru.process.exception.badrequest.ProcessNameLengthException;
import com.cruru.util.fixture.DashboardFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("프로세스 도메인 테스트")
class ProcessTest {

    @DisplayName("프로세스 이름은 특수문자와 숫자를 허용한다.")
    @ValueSource(strings = {"process!!", "(process!@#$%^&*)", "PROCESS123"})
    @ParameterizedTest
    void validProcessName(String name) {
        // given
        Dashboard dashboard = DashboardFixture.backend();

        // when&then
        assertThatCode(() -> new Process(0, name, "desc", dashboard)).doesNotThrowAnyException();
    }

    @DisplayName("프로세스 이름이 비어있으면 예외가 발생한다.")
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void processNameBlank(String name) {
        // given
        Dashboard dashboard = new Dashboard(null);

        // when&then
        assertThatThrownBy(() -> new Process(0, name, "desc", dashboard))
                .isInstanceOf(ProcessNameBlankException.class);
    }

    @DisplayName("프로세스 이름이 32자 초과시 예외가 발생한다.")
    @Test
    void invalidProcessNameLength() {
        // given
        Dashboard dashboard = new Dashboard(null);

        // when&then
        assertThatThrownBy(() -> new Process(0, "ThisStringLengthIs33!!!!!!!!!!!!!", "desc", dashboard))
                .isInstanceOf(ProcessNameLengthException.class);
    }

    @DisplayName("프로세스 이름에 허용되지 않은 글자가 들어가면 예외가 발생한다.")
    @ValueSource(strings = {"invalidCharacter|", "invalidCharacter\\"})
    @ParameterizedTest
    void invalidProcessNameCharacter(String name) {
        // given
        Dashboard dashboard = new Dashboard(null);

        // when&then
        assertThatThrownBy(() -> new Process(0, name, "desc", dashboard))
                .isInstanceOf(ProcessNameCharacterException.class);
    }
}
