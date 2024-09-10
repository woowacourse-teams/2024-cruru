package com.cruru.process.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.controller.response.ApplicantCardResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.domain.repository.EvaluationRepository;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessResponse;
import com.cruru.process.controller.dto.ProcessResponses;
import com.cruru.process.controller.dto.ProcessUpdateRequest;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
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
        processFacade.create(loginProfile, processCreateRequest, defaultDashboard.getId());

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
        Process process = processRepository.save(ProcessFixture.applyType(defaultDashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        List<Evaluation> evaluations = List.of(
                EvaluationFixture.fivePoints(process, applicant),
                EvaluationFixture.fourPoints(process, applicant)
        );
        evaluationRepository.saveAll(evaluations);

        // when
        ProcessResponses processResponses = processFacade.readAllByDashboardId(loginProfile, defaultDashboard.getId());

        // then
        ProcessResponse firstProcessResponse = processResponses.processResponses().get(0);
        long processId = firstProcessResponse.id();
        ApplicantCardResponse applicantCardResponse = firstProcessResponse.applicantCardResponses().get(0);
        assertAll(
                () -> assertThat(processResponses.processResponses()).hasSize(1),
                () -> assertThat(processId).isEqualTo(process.getId()),
                () -> assertThat(applicantCardResponse.id()).isEqualTo(applicant.getId()),
                () -> assertThat(applicantCardResponse.evaluationCount()).isEqualTo(evaluations.size()),
                () -> assertThat(applicantCardResponse.averageScore()).isEqualTo(4.5)
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
        ProcessResponse actualProcessResponse = processFacade.update(loginProfile, processUpdateRequest, processId);

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
        processFacade.delete(loginProfile, process.getId());

        // then
        assertThat(processRepository.findAll()).isEmpty();
    }
}
