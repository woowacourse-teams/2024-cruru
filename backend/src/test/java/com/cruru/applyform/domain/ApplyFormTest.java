package com.cruru.applyform.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.applyform.exception.badrequest.StartDateAfterEndDateException;
import com.cruru.applyform.exception.badrequest.StartDatePastException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("지원서 폼 도메인 테스트")
class ApplyFormTest {

    @DisplayName("시작 날짜가 마감 날짜보다 늦을 경우 예외가 발생한다.")
    @Test
    void invalidDate() {
        // given
        String title = "title";
        String description = "description";
        LocalDateTime startDate = LocalDateTime.now().plusHours(5);
        LocalDateTime endDate = LocalDateTime.now().plusHours(2);

        // when&then
        assertThatThrownBy(() -> new ApplyForm(title, description, startDate, endDate, null))
                .isInstanceOf(StartDateAfterEndDateException.class);
    }

    @DisplayName("시작 날짜가 현재 날짜보다 이전일 경우 예외가 발생한다.")
    @Test
    void startDateInPast() {
        // given
        String title = "title";
        String description = "description";
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        // when&then
        assertThatThrownBy(() -> new ApplyForm(title, description, startDate, endDate, null))
                .isInstanceOf(StartDatePastException.class);
    }
}
