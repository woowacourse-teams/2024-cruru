package com.cruru.applyform.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.cruru.applyform.controller.dto.ApplyFormCreateRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applyform.exception.ApplyFormNotFoundException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지원서 폼 서비스 테스트")
class ApplyFormServiceTest extends ServiceTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ApplyFormService applyFormService;

    private Dashboard dashboard;

    @BeforeEach
    void setUp() {
        dashboard = dashboardRepository.save(DashboardFixture.backend());
    }

    @DisplayName("지원공고를 성공적으로 생성한다.")
    @Test
    void create() {
        // given
        String title = "우아한테크코스 백엔드 7기 모집";
        String postingContent = "# 모집합니다! ## 사실 안모집합니다";
        LocalDateTime startDate = LocalDateTime.of(2099, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2099, 12, 31, 23, 59);
        ApplyFormCreateRequest request = new ApplyFormCreateRequest(title, postingContent, startDate, endDate);

        // when
        ApplyForm savedApplyForm = applyFormService.create(request, dashboard);
        long applyFormId = savedApplyForm.getId();
        String applyPostUrl = String.format("www.cruru.kr/post/%d", applyFormId);

        // then
        ApplyForm actualApplyForm = applyFormRepository.findById(applyFormId).get();
        assertAll(
                () -> assertThat(actualApplyForm.getTitle()).isEqualTo(title),
                () -> assertThat(actualApplyForm.getDescription()).isEqualTo(postingContent),
                () -> assertThat(actualApplyForm.getUrl()).isEqualTo(applyPostUrl),
                () -> assertThat(actualApplyForm.getStartDate()).isEqualTo(startDate),
                () -> assertThat(actualApplyForm.getEndDate()).isEqualTo(endDate)
        );
    }

    @DisplayName("지원서 폼 질문 조회에 성공한다.")
    @Test
    void findById() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        // when
        ApplyForm actualApplyForm = applyFormService.findById(applyForm.getId());

        // then
        assertAll(
                () -> assertThat(actualApplyForm.getTitle()).isEqualTo(applyForm.getTitle()),
                () -> assertThat(actualApplyForm.getStartDate()).isEqualTo(applyForm.getStartDate()),
                () -> assertThat(actualApplyForm.getEndDate()).isEqualTo(applyForm.getEndDate())
        );
    }

    @DisplayName("지원서 폼 조회 시, 지원서 폼이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void findById_invalidApplyForm() {
        // given
        processRepository.save(ProcessFixture.first(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.frontend(dashboard));
        questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        // when&then
        assertThatThrownBy(() -> applyFormService.findById(-1)).isInstanceOf(ApplyFormNotFoundException.class);
    }

    @DisplayName("대시보드 ID로 지원폼을 조회한다.")
    @Test
    void findByDashboard() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));

        // when&then
        assertDoesNotThrow(() -> applyFormService.findByDashboard(dashboard));
        assertThat(applyFormService.findByDashboard(dashboard)).isEqualTo(applyForm);
    }
}
