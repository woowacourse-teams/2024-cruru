package com.cruru.process.service;

import static com.cruru.util.fixture.ApplicantFixture.createApplicantDobby;
import static com.cruru.util.fixture.DashboardFixture.createBackendDashboard;
import static com.cruru.util.fixture.ProcessFixture.createFinalProcess;
import static com.cruru.util.fixture.ProcessFixture.createFirstProcess;
import static com.cruru.util.fixture.ProcessFixture.createInterviewProcess;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.dashboard.exception.DashboardNotFoundException;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessResponse;
import com.cruru.process.controller.dto.ProcessUpdateRequest;
import com.cruru.process.controller.dto.ProcessesResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.process.exception.ProcessCountException;
import com.cruru.process.exception.ProcessDeleteEndsException;
import com.cruru.process.exception.ProcessDeleteRemainingApplicantException;
import com.cruru.util.ServiceTest;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("프로세스 서비스 테스트")
class ProcessServiceTest extends ServiceTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProcessService processService;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @DisplayName("ID에 해당하는 대시보드의 프로세스 목록과 지원자 정보를 조회한다.")
    @Test
    void findByDashboardId() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        Process process = processRepository.save(createFirstProcess(dashboard));
        Applicant applicant = applicantRepository.save(createApplicantDobby(process));
        evaluationRepository.save(new Evaluation(5, "하드 스킬과 소프트 스킬이 출중함.", process, applicant));

        // when
        ProcessesResponse byDashboardId = processService.findByDashboardId(dashboard.getId());

        // then
        ProcessResponse firstProcessResponse = byDashboardId.processResponses().get(0);
        assertAll(
                () -> assertThat(byDashboardId.processResponses()).hasSize(1),
                () -> assertThat(firstProcessResponse.processId()).isEqualTo(process.getId()),
                () -> assertThat(firstProcessResponse.dashboardApplicantResponses().get(0).applicantId()).isEqualTo(
                        applicant.getId()),
                () -> assertThat(firstProcessResponse.dashboardApplicantResponses().get(0).evaluationCount()).isEqualTo(
                        1)
        );
    }

    @DisplayName("대시보드가 존재하지 않는 경우, 예외가 발생한다.")
    @Test
    void invalidDashboardId() {
        // given
        long invalidId = 0;

        // when&then
        assertThatThrownBy(() -> processService.findByDashboardId(invalidId))
                .isInstanceOf(DashboardNotFoundException.class);
    }

    @DisplayName("새로운 프로세스를 생성한다.")
    @Test
    void create() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        processRepository.save(createFirstProcess(dashboard));
        processRepository.save(createFinalProcess(dashboard));
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("1차 면접", "화상 면접", 1);

        // when
        processService.create(processCreateRequest, dashboard.getId());

        // then
        List<Process> allByDashboardId = processRepository.findAllByDashboardId(dashboard.getId())
                .stream()
                .sorted(Comparator.comparingInt(Process::getSequence))
                .toList();

        assertThat(allByDashboardId).hasSize(3);
        assertThat(allByDashboardId.get(1).getName()).isEqualTo("1차 면접");
    }

    @DisplayName("프로세스 최대 개수를 초과하면, 예외가 발생한다.")
    @Test
    void createOverProcessMaxCount() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        processRepository.saveAll(
                List.of(
                        new Process(0, "서류", "서류", dashboard),
                        new Process(1, "코딩 테스트", "온라인", dashboard),
                        new Process(2, "CS 테스트", "온라인", dashboard),
                        new Process(3, "1차 면접", "화상 면접", dashboard),
                        new Process(4, "최종 면접", "대면 면접", dashboard)
                )
        );
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("2차 면접", "화상 면접", 1);

        // when&then
        assertThatThrownBy(() -> processService.create(processCreateRequest, dashboard.getId()))
                .isInstanceOf(ProcessCountException.class);
    }

    @DisplayName("프로세스 정보를 변경한다.")
    @Test
    void update() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        Process process = processRepository.save(createFirstProcess(dashboard));
        ProcessUpdateRequest processUpdateRequest = new ProcessUpdateRequest("면접 수정", "수정된 설명");

        // when
        long processId = process.getId();
        ProcessResponse actualProcessResponse = processService.update(processUpdateRequest, processId);

        // then
        assertAll(() -> {
            assertThat(actualProcessResponse.name()).isEqualTo(processUpdateRequest.name());
            assertThat(actualProcessResponse.description()).isEqualTo(processUpdateRequest.description());
        });
    }

    @DisplayName("프로세스를 삭제한다.")
    @Test
    void delete() {
        // given;
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        Process process = processRepository.save(createInterviewProcess(dashboard));

        // when
        processService.delete(process.getId());

        // then
        assertThat(processRepository.findAll()).hasSize(0);
    }

    @DisplayName("첫번째 프로세스 혹은 마지막 프로세스를 삭제하면 예외가 발생한다.")
    @Test
    void deleteFirstOrLastProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        Process firstProcess = processRepository.save(createFirstProcess(dashboard));
        Process finalProcess = processRepository.save(createFinalProcess(dashboard));

        // when&then
        assertAll(
                () -> assertThatThrownBy(() -> processService.delete(firstProcess.getId()))
                        .isInstanceOf(ProcessDeleteEndsException.class),
                () -> assertThatThrownBy(() -> processService.delete(finalProcess.getId()))
                        .isInstanceOf(ProcessDeleteEndsException.class)
        );
    }

    @DisplayName("삭제하려는 프로세스에 해당되는 지원자가 있을 경우 예외가 발생한다.")
    @Test
    void deleteExistsApplicantProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        Process process = processRepository.save(createInterviewProcess(dashboard));
        applicantRepository.save(createApplicantDobby(process));

        // when&then
        assertThatThrownBy(() -> processService.delete(process.getId()))
                .isInstanceOf(ProcessDeleteRemainingApplicantException.class);
    }
}
