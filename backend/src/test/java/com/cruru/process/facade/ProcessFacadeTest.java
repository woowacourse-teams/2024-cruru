package com.cruru.process.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.controller.response.ApplicantCardResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.domain.repository.EvaluationRepository;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.process.controller.request.ProcessCreateRequest;
import com.cruru.process.controller.request.ProcessUpdateRequest;
import com.cruru.process.controller.response.ProcessResponse;
import com.cruru.process.controller.response.ProcessResponses;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DefaultFilterAndOrderFixture;
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
        processRepository.save(ProcessFixture.applyType(defaultDashboard));
        processRepository.save(ProcessFixture.approveType(defaultDashboard));
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("새로운 프로세스", "기존 2개의 프로세스 사이에 생성.", 1);

        // when
        processFacade.create(processCreateRequest, defaultDashboard.getId());

        // then
        List<Process> allByDashboardId = processRepository.findAllByDashboardId(defaultDashboard.getId())
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
        applyFormRepository.save(ApplyFormFixture.backend(defaultDashboard));
        Process process1 = processRepository.save(ProcessFixture.applyType(defaultDashboard));
        Process process2 = processRepository.save(ProcessFixture.interview(defaultDashboard));
        Applicant applicant1 = applicantRepository.save(ApplicantFixture.pendingDobby(process1));
        Applicant applicant2 = applicantRepository.save(ApplicantFixture.pendingDobby(process1));
        List<Evaluation> evaluations1 = List.of(
                EvaluationFixture.fivePoints(process1, applicant1),
                EvaluationFixture.fourPoints(process1, applicant1),
                EvaluationFixture.fourPoints(process1, applicant1),
                EvaluationFixture.fourPoints(process1, applicant1)
        );
        evaluationRepository.saveAll(evaluations1);

        List<Evaluation> evaluations2 = List.of(
                EvaluationFixture.fivePoints(process2, applicant2),
                EvaluationFixture.fivePoints(process2, applicant2),
                EvaluationFixture.fivePoints(process2, applicant2),
                EvaluationFixture.fourPoints(process2, applicant2)
        );
        evaluationRepository.saveAll(evaluations2);
        String sortByCreatedAt = "DESC";
        String sortByScore = null;

        // when
        ProcessResponses processResponses = processFacade.readAllByDashboardId(
                defaultDashboard.getId(),
                DefaultFilterAndOrderFixture.DEFAULT_MIN_SCORE,
                DefaultFilterAndOrderFixture.DEFAULT_MAX_SCORE,
                DefaultFilterAndOrderFixture.DEFAULT_EVALUATION_STATUS,
                sortByCreatedAt,
                sortByScore
        );

        // then
        ProcessResponse firstProcessResponse = processResponses.processResponses().get(0);
        long processId = firstProcessResponse.id();
        ApplicantCardResponse applicantCardResponse = firstProcessResponse.applicantCardResponses().get(0);
        assertAll(
                () -> assertThat(processResponses.processResponses()).hasSize(2),
                () -> assertThat(processId).isEqualTo(process1.getId()),
                () -> assertThat(applicantCardResponse.id()).isEqualTo(applicant2.getId()),
                () -> assertThat(applicantCardResponse.evaluationCount()).isEqualTo(evaluations1.size()),
                () -> assertThat(applicantCardResponse.averageScore()).isEqualTo(4.75)
        );
    }

    @DisplayName("프로세스 정보를 변경한다.")
    @Test
    void update() {
        // given
        Process process = processRepository.save(ProcessFixture.applyType(defaultDashboard));
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
        Process process = processRepository.save(ProcessFixture.interview(defaultDashboard));

        // when
        processFacade.delete(process.getId());

        // then
        assertThat(processRepository.findAll()).isEmpty();
    }
}
