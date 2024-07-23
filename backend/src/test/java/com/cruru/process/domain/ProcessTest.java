package com.cruru.process.domain;

import static com.cruru.fixture.DashboardFixture.createBackendDashboard;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.exception.ProcessBadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("프로세스 도메인 테스트")
class ProcessTest {

    @DisplayName("잘못된 프로세스 이름으로 생성 시 예외가 발생한다.")
    @ValueSource(strings = {"", "thisstringmustexceedthirtytwochar", "invalidSpecialChar--"})
    @ParameterizedTest
    void invalidProcessName(String invalidName) {
        // given
        Dashboard dashboard = createBackendDashboard();

        // when&then
        assertThatThrownBy(() -> new Process(1, invalidName, "온라인", dashboard))
                .isInstanceOf(ProcessBadRequestException.class);
    }
}
