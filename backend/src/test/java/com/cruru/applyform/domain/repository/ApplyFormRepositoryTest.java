package com.cruru.applyform.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.DashboardApplyFormDto;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.LocalDateFixture;
import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    private ClubRepository clubRepository;

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
        LocalDateTime startDate = LocalDateFixture.oneDayLater();
        LocalDateTime endDate = LocalDateFixture.oneWeekLater();

        ApplyForm expectedApplyForm = applyFormRepository.save(new ApplyForm(
                initialApplyForm.getId(),
                title,
                description,
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
        assertThat(savedApplyForm1.getId()).isLessThan(savedApplyForm2.getId());
    }

    @DisplayName("특정 동아리에 속하는 DashboardApplyForm 목록을 반환한다.")
    @Test
    void findAllByClub() {
        // given
        Club club = clubRepository.save(ClubFixture.create());
        Dashboard dashboard1 = dashboardRepository.save(DashboardFixture.frontend(club));
        Dashboard dashboard2 = dashboardRepository.save(DashboardFixture.backend(club));

        ApplyForm applyForm1 = applyFormRepository.save(ApplyFormFixture.frontend(dashboard1));
        ApplyForm applyForm2 = applyFormRepository.save(ApplyFormFixture.backend(dashboard2));

        // when
        List<DashboardApplyFormDto> dashboardApplyFormDtos = applyFormRepository.findAllByClub(club.getId());

        // then
        assertThat(dashboardApplyFormDtos).hasSize(2);

        DashboardApplyFormDto dto1 = dashboardApplyFormDtos.get(0);
        DashboardApplyFormDto dto2 = dashboardApplyFormDtos.get(1);

        assertAll(
                () -> assertThat(dto1.dashboard().getId()).isEqualTo(dashboard1.getId()),
                () -> assertThat(dto1.applyForm().getId()).isEqualTo(applyForm1.getId()),
                () -> assertThat(dto2.dashboard().getId()).isEqualTo(dashboard2.getId()),
                () -> assertThat(dto2.applyForm().getId()).isEqualTo(applyForm2.getId())
        );
    }
}
