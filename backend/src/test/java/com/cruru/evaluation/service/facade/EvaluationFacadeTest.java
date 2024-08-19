package com.cruru.evaluation.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.evaluation.controller.dto.EvaluationResponse;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.ProcessFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("평가 파사드 서비스 테스트")
class EvaluationFacadeTest extends ServiceTest {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationFacade evaluationFacade;

    private Process process;

    private Applicant applicant;

    @BeforeEach
    void setUp() {
        process = processRepository.save(ProcessFixture.first());
        applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
    }

    @DisplayName("평가 등록 요청 정보로 평가를 생성한다.")
    @Test
    void create() {
        // given
        Evaluation evaluation = EvaluationFixture.fivePoints();
        Integer score = evaluation.getScore();
        String content = evaluation.getContent();
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);

        // when
        evaluationFacade.create(request, process.getId(), applicant.getId());

        // then
        List<Evaluation> evaluations = evaluationRepository.findAllByProcessAndApplicant(process, applicant);
        Evaluation actualEvaluation = evaluations.get(0);
        assertAll(
                () -> assertThat(evaluations).hasSize(1),
                () -> assertThat(actualEvaluation.getScore()).isEqualTo(score),
                () -> assertThat(actualEvaluation.getContent()).isEqualTo(content)
        );
    }

    @DisplayName("특정 지원자의 해당 프로세스에서의 평가 내용을 조회한다.")
    @Test
    void readEvaluationsOfApplicantInProcess() {
        // given
        Evaluation evaluationExcellent = EvaluationFixture.fivePoints(process, applicant);
        Evaluation evaluationGood = EvaluationFixture.fourPoints(process, applicant);
        Evaluation evaluation1 = evaluationRepository.save(evaluationExcellent);
        Evaluation evaluation2 = evaluationRepository.save(evaluationGood);
        Evaluation savedEvaluation1 = evaluationRepository.findById(evaluation1.getId()).get();
        Evaluation savedEvaluation2 = evaluationRepository.findById(evaluation2.getId()).get();

        // when
        List<EvaluationResponse> evaluationResponses = evaluationFacade.readEvaluationsOfApplicantInProcess(
                process.getId(),
                applicant.getId()
        ).evaluationsResponse();

        // then
        EvaluationResponse actualEvaluation1 = evaluationResponses.get(0);
        EvaluationResponse actualEvaluation2 = evaluationResponses.get(1);
        assertAll(
                () -> assertThat(evaluationResponses).hasSize(2),

                () -> assertThat(actualEvaluation1.evaluationId()).isEqualTo(savedEvaluation1.getId()),
                () -> assertThat(actualEvaluation1.content()).isEqualTo(savedEvaluation1.getContent()),
                () -> assertThat(actualEvaluation1.score()).isEqualTo(savedEvaluation1.getScore()),
                () -> assertThat(actualEvaluation1.createdDate()).isEqualTo(savedEvaluation1.getCreatedDate()),

                () -> assertThat(actualEvaluation2.evaluationId()).isEqualTo(savedEvaluation2.getId()),
                () -> assertThat(actualEvaluation2.content()).isEqualTo(savedEvaluation2.getContent()),
                () -> assertThat(actualEvaluation2.score()).isEqualTo(savedEvaluation2.getScore()),
                () -> assertThat(actualEvaluation2.createdDate()).isEqualTo(savedEvaluation2.getCreatedDate())
        );

    }
}
