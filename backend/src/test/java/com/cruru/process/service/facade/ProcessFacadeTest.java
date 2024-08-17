package com.cruru.process.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessResponse;
import com.cruru.process.controller.dto.ProcessResponses;
import com.cruru.process.controller.dto.ProcessUpdateRequest;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.ProcessFixture;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("프로세스 파사드 서비스 테스트")
class ProcessFacadeTest extends ServiceTest {

    @Autowired
    private ProcessFacade processFacade;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @DisplayName("새로운 프로세스를 생성한다.")
    @Test
    void create() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.first(dashboard));
        processRepository.save(ProcessFixture.last(dashboard));
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("새로운 프로세스", "기존 2개의 프로세스 사이에 생성.", 1);

        // when
        processFacade.create(processCreateRequest, dashboard.getId());

        // then
        List<Process> allByDashboardId = processRepository.findAllByDashboardId(dashboard.getId())
                .stream()
                .sorted(Comparator.comparingInt(Process::getSequence))
                .toList();

        String newProcessName = allByDashboardId.get(1).getName();
        assertAll(
                () -> assertThat(allByDashboardId).hasSize(3),
                () -> assertThat(newProcessName).isEqualTo("새로운 프로세스")
        );
    }

    @DisplayName("ID에 해당하는 대시보드의 프로세스 목록과 지원자 정보를 조회한다.")
    @Test
    void readAllByDashboardId() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Process process = processRepository.save(ProcessFixture.first(dashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        evaluationRepository.save(EvaluationFixture.fivePoints(process, applicant));

        // when
        ProcessResponses processResponses = processFacade.readAllByDashboardId(dashboard.getId());

        // then
        ProcessResponse firstProcessResponse = processResponses.processResponses().get(0);
        long processId = firstProcessResponse.id();
        long applicantCardId = firstProcessResponse.applicantCardResponses().get(0).id();
        int applicantEvaluationCount = firstProcessResponse.applicantCardResponses().get(0).evaluationCount();
        assertAll(
                () -> assertThat(processResponses.processResponses()).hasSize(1),
                () -> assertThat(processId).isEqualTo(process.getId()),
                () -> assertThat(applicantCardId).isEqualTo(applicant.getId()),
                () -> assertThat(applicantEvaluationCount).isEqualTo(1)
        );
    }

    @DisplayName("프로세스 정보를 변경한다.")
    @Test
    void update() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        Process process = processRepository.save(ProcessFixture.first(dashboard));
        ProcessUpdateRequest processUpdateRequest = new ProcessUpdateRequest("면접 수정", "수정된 설명");

        // when
        Long processId = process.getId();
        ProcessResponse actualProcessResponse = processFacade.update(processUpdateRequest, processId);

        // then
        assertAll(
                () -> assertThat(actualProcessResponse.name()).isEqualTo(processUpdateRequest.name()),
                () -> assertThat(actualProcessResponse.description()).isEqualTo(processUpdateRequest.description())
        );
    }

    @DisplayName("프로세스를 삭제한다.")
    @Test
    void delete() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        Process process = processRepository.save(ProcessFixture.interview(dashboard));

        // when
        processFacade.delete(process.getId());

        // then
        assertThat(processRepository.findAll()).isEmpty();
    }
}
