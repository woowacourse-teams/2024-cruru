package com.cruru.evaluation.service;

import static com.cruru.util.fixture.ApplicantFixture.createPendingApplicantDobby;
import static com.cruru.util.fixture.EvaluationFixture.createEvaluationExcellent;
import static com.cruru.util.fixture.ProcessFixture.createFirstProcess;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.evaluation.controller.dto.EvaluationResponse;
import com.cruru.evaluation.controller.dto.EvaluationUpdateRequest;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.evaluation.exception.EvaluationNotFoundException;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
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
        process = processRepository.save(createFirstProcess());

        applicant = applicantRepository.save(createPendingApplicantDobby(process));
    }

    @DisplayName("새로운 평가를 생성한다.")
    @Test
    void create() {
        // given
        int score = 4;
        String content = "서류가 인상적입니다.";

        // when
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);
        evaluationService.create(request, process.getId(), applicant.getId());

        // then
        List<Evaluation> evaluations = evaluationRepository.findAllByProcessAndApplicant(process, applicant);
        assertAll(
                () -> assertThat(evaluations).hasSize(1),
                () -> assertThat(evaluations.get(0).getScore()).isEqualTo(score),
                () -> assertThat(evaluations.get(0).getContent()).isEqualTo(content)
        );
    }

    @DisplayName("평가를 조회한다.")
    @Test
    void read() {
        // given
        int score = 1;
        String content = "인재상과 맞지 않습니다.";
        Evaluation evaluation = evaluationRepository.save(new Evaluation(score, content, process, applicant));

        // when
        List<EvaluationResponse> responses = evaluationService.read(process.getId(), applicant.getId())
                .evaluationsResponse();

        // then
        assertAll(
                () -> assertThat(responses).hasSize(1),
                () -> assertThat(responses.get(0).evaluationId()).isEqualTo(evaluation.getId()),
                () -> assertThat(responses.get(0).score()).isEqualTo(score),
                () -> assertThat(responses.get(0).content()).isEqualTo(content)
        );
    }

    @DisplayName("평가 수정에 성공한다.")
    @Test
    void update() {
        // given
        Evaluation evaluation = evaluationRepository.save(createEvaluationExcellent());
        int score = 2;
        String content = "맞춤법이 틀렸습니다.";
        EvaluationUpdateRequest request = new EvaluationUpdateRequest(score, content);

        // when
        evaluationService.update(request, evaluation.getId());

        // then
        Optional<Evaluation> updatedEvaluation = evaluationRepository.findById(evaluation.getId());

        assertAll(() -> {
            assertThat(updatedEvaluation).isPresent();
            assertThat(updatedEvaluation.get().getScore()).isEqualTo(score);
            assertThat(updatedEvaluation.get().getContent()).isEqualTo(content);
        });
    }

    @DisplayName("평가 수정 시, 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void update_evaluationNotFound() {
        // given
        long invalidId = -1;
        int score = 2;
        String content = "맞춤법이 틀렸습니다.";
        EvaluationUpdateRequest request = new EvaluationUpdateRequest(score, content);

        // when&then
        assertThatThrownBy(() -> evaluationService.update(request, invalidId))
                .isInstanceOf(EvaluationNotFoundException.class);
    }
}
