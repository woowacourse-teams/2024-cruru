package com.cruru.applyform.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지원서 레포지토리 테스트")
class ApplyFormRepositoryTest extends RepositoryTest {

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @BeforeEach
    void setUp() {
        applyFormRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어있는 ID를 가진 지원서를 저장하면, 해당 ID의 지원서는 가장 최근 저장된 정보로 업데이트된다.")
    @Test
    void save_ApplyFormIdUpdate() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        ApplyForm applyForm = ApplyFormFixture.backend(dashboard);
        ApplyForm initialApplyForm = applyFormRepository.save(applyForm);

        // when
        String title = "수정된 공고 제목";
        String description = "수정된 상세 내용";
        String url = "www.modified.url";
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59);

        ApplyForm expectedApplyForm = applyFormRepository.save(new ApplyForm(
                initialApplyForm.getId(),
                title,
                description,
                url,
                startDate,
                endDate,
                initialApplyForm.getDashboard()
        ));

        // then
        ApplyForm actualApplyForm = applyFormRepository.findById(expectedApplyForm.getId()).get();
        assertAll(
                () -> assertThat(actualApplyForm.getDashboard()).isEqualTo(applyForm.getDashboard()),
                () -> assertThat(actualApplyForm.getTitle()).isEqualTo(title),
                () -> assertThat(actualApplyForm.getDescription()).isEqualTo(description),
                () -> assertThat(actualApplyForm.getUrl()).isEqualTo(url),
                () -> assertThat(actualApplyForm.getStartDate()).isEqualTo(startDate),
                () -> assertThat(actualApplyForm.getEndDate()).isEqualTo(endDate)
        );
    }

    @DisplayName("ID가 없는 지원서 양식을 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void save_NotSavedId() {
        //given
        Dashboard dashboard1 = dashboardRepository.save(DashboardFixture.backend());
        Dashboard dashboard2 = dashboardRepository.save(DashboardFixture.frontend());
        ApplyForm applyForm1 = ApplyFormFixture.backend(dashboard1);
        ApplyForm applyForm2 = ApplyFormFixture.frontend(dashboard2);

        //when
        ApplyForm savedApplyForm1 = applyFormRepository.save(applyForm1);
        ApplyForm savedApplyForm2 = applyFormRepository.save(applyForm2);

        //then
        assertThat(savedApplyForm1.getId() + 1).isEqualTo(savedApplyForm2.getId());
    }
}
