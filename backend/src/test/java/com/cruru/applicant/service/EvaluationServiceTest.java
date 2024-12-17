package com.cruru.applicant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.controller.request.EvaluationCreateRequest;
import com.cruru.applicant.controller.request.EvaluationUpdateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.domain.repository.EvaluationRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.ProcessFixture;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("평가 서비스 테스트")
class EvaluationServiceTest extends ServiceTest {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationService evaluationService;

    private Process process;

    private Applicant applicant;

    @BeforeEach
    void setUp() {
        process = processRepository.save(ProcessFixture.applyType());

        applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
    }

    @DisplayName("새로운 평가를 생성한다.")
    @Test
    void create() {
        // given
        int score = 4;
        String content = "서류가 인상적입니다.";

        // when
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);
        evaluationService.create(request, process, applicant);

        // then
        List<Evaluation> evaluations = evaluationRepository.findAllByProcessAndApplicant(process, applicant);
        Evaluation evaluation = evaluations.get(0);
        assertAll(
                () -> assertThat(evaluations).hasSize(1),
                () -> assertThat(evaluation.getScore()).isEqualTo(score),
                () -> assertThat(evaluation.getContent()).isEqualTo(content)
        );
    }

    @DisplayName("지원자와 프로세스를 통해 평가를 조회한다.")
    @Test
    void findAllByProcessAndApplicant() {
        // given
        int score = 1;
        String content = "인재상과 맞지 않습니다.";
        Evaluation evaluation = evaluationRepository.save(new Evaluation(score, content, process, applicant));

        // when
        List<Evaluation> savedEvaluations = evaluationService.findAllByProcessAndApplicant(process, applicant);

        // then
        Evaluation actualEvaluation = savedEvaluations.get(0);
        assertAll(
                () -> assertThat(savedEvaluations).hasSize(1),
                () -> assertThat(actualEvaluation.getId()).isEqualTo(evaluation.getId()),
                () -> assertThat(actualEvaluation.getScore()).isEqualTo(score),
                () -> assertThat(actualEvaluation.getContent()).isEqualTo(content)
        );
    }

    @DisplayName("평가 수정에 성공한다.")
    @Test
    void update() {
        // given
        Evaluation evaluation = evaluationRepository.save(EvaluationFixture.fivePoints());
        int score = 1;
        String content = "수정된 평가입니다.";
        EvaluationUpdateRequest request = new EvaluationUpdateRequest(score, content);

        // when
        evaluationService.update(request, evaluation);

        // then
        Optional<Evaluation> updatedEvaluation = evaluationRepository.findById(evaluation.getId());

        assertAll(
                () -> assertThat(updatedEvaluation).isPresent(),
                () -> assertThat(updatedEvaluation.get().getScore()).isEqualTo(score),
                () -> assertThat(updatedEvaluation.get().getContent()).isEqualTo(content)
        );
    }

    @DisplayName("입력된 프로세스에 해당하는 평가들을 삭제한다.")
    @Test
    void deleteByProcess() {
        // given
        Process process1 = processRepository.save(ProcessFixture.applyType());
        Process process2 = processRepository.save(ProcessFixture.approveType());

        Evaluation evaluation1 = evaluationRepository.save(EvaluationFixture.fivePoints(process1, null));
        Evaluation evaluation2 = evaluationRepository.save(EvaluationFixture.fivePoints(process1, null));
        Evaluation evaluation3 = evaluationRepository.save(EvaluationFixture.fivePoints(process2, null));

        // when
        evaluationService.deleteByProcess(process1.getId());

        // then
        assertThat(evaluationRepository.findAll()).contains(evaluation3)
                .doesNotContain(evaluation1, evaluation2);
    }

    @DisplayName("입력된 프로세스 목록에 해당하는 평가들을 삭제한다.")
    @Test
    void deleteAllByProcesses() {
        // given
        Process process1 = processRepository.save(ProcessFixture.applyType());
        Process process2 = processRepository.save(ProcessFixture.interview(null));
        Process process3 = processRepository.save(ProcessFixture.approveType());
        List<Process> processes = List.of(process1, process2);

        Evaluation evaluation1 = evaluationRepository.save(EvaluationFixture.fivePoints(process1, null));
        Evaluation evaluation2 = evaluationRepository.save(EvaluationFixture.fivePoints(process2, null));
        Evaluation evaluation3 = evaluationRepository.save(EvaluationFixture.fivePoints(process3, null));

        // when
        evaluationService.deleteAllByProcesses(processes);

        // then
        assertThat(evaluationRepository.findAll()).contains(evaluation3)
                .doesNotContain(evaluation1, evaluation2);
    }

    @DisplayName("평가를 삭제한다.")
    @Test
    void delete() {
        // given
        Evaluation evaluation = evaluationRepository.save(EvaluationFixture.fivePoints());

        // when
        evaluationService.delete(evaluation.getId());

        // then
        assertThat(evaluationRepository.findById(evaluation.getId())).isEmpty();
    }
}
