package com.cruru.process.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.exception.ProcessBadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("프로세스 도메인 테스트")
class ProcessTest {

    @DisplayName("잘못된 프로세스 이름으로 생성 시 예외가 발생한다.")
    @ValueSource(strings = {"", "abcdefghabcdefghabcdefghabcdefgh!", "process--"})
    @ParameterizedTest
    void InvalidProcessName(String InvalidName) {
        // given
        Dashboard dashboard = new Dashboard("대시보드", null);

        // when&then
        assertThatThrownBy(() -> new Process(1, InvalidName, "설명", dashboard))
                .isInstanceOf(ProcessBadRequestException.class);
    }
}
