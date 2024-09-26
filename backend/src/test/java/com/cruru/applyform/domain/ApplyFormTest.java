package com.cruru.applyform.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.applyform.exception.badrequest.StartDateAfterEndDateException;
import com.cruru.util.fixture.LocalDateFixture;
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
        LocalDateTime startDate = LocalDateFixture.oneWeekLater();
        LocalDateTime endDate = LocalDateFixture.oneDayLater();

        // when&then
        assertThatThrownBy(() -> new ApplyForm(title, description, startDate, endDate, null))
                .isInstanceOf(StartDateAfterEndDateException.class);
    }
}
