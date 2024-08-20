package com.cruru.applyform.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applyform.exception.badrequest.StartDateAfterEndDateException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
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
        Dashboard dashboard = DashboardFixture.backend();
        ApplyForm applyForm = ApplyFormFixture.backend(dashboard);

        // when&then
        assertAll(
                () -> assertThatThrownBy(() -> new ApplyForm(title, description, startDate, endDate, null))
                        .isInstanceOf(StartDateAfterEndDateException.class),
                () -> assertThatThrownBy(() -> applyForm.updateApplyForm("new title", "new desc", startDate, endDate))
                        .isInstanceOf(StartDateAfterEndDateException.class)
        );
    }

    @DisplayName("지원서 폼을 수정한다.")
    @Test
    void update() {
        // given
        String toChangeTitle = "크루루 백엔드 모집 공고~~";
        String toChangeDescription = "# 모집 공고 설명 #";
        LocalDateTime toChangeStartDate = LocalDateTime.of(2099, 11, 30, 23, 59, 59);
        LocalDateTime toChangeEndDate = LocalDateTime.of(2099, 12, 25, 23, 59, 59);

        Dashboard dashboard = DashboardFixture.backend();
        ApplyForm applyForm = ApplyFormFixture.backend(dashboard);

        // when
        applyForm.updateApplyForm(toChangeTitle, toChangeDescription, toChangeStartDate, toChangeEndDate);

        // then
        assertAll(
                () -> assertThat(applyForm.getTitle()).isEqualTo(toChangeTitle),
                () -> assertThat(applyForm.getDescription()).isEqualTo(toChangeDescription),
                () -> assertThat(applyForm.getStartDate()).isEqualTo(toChangeStartDate),
                () -> assertThat(applyForm.getEndDate()).isEqualTo(toChangeEndDate)
        );
    }
}
