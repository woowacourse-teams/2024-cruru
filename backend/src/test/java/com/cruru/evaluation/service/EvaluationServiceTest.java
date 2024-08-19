package com.cruru.evaluation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.evaluation.controller.dto.EvaluationUpdateRequest;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.evaluation.exception.EvaluationNotFoundException;
import com.cruru.evaluation.exception.badrequest.EvaluationNoChangeException;
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
        process = processRepository.save(ProcessFixture.first());

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
        evaluationService.update(request, evaluation.getId());

        // then
        Optional<Evaluation> updatedEvaluation = evaluationRepository.findById(evaluation.getId());

        assertAll(
                () -> assertThat(updatedEvaluation).isPresent(),
                () -> assertThat(updatedEvaluation.get().getScore()).isEqualTo(score),
                () -> assertThat(updatedEvaluation.get().getContent()).isEqualTo(content)
        );
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

    @DisplayName("평가 수정 시, 수정된 내용이 없을 경우 예외가 발생한다.")
    @Test
    void update_evaluationNotChanged() {
        // given
        Evaluation evaluation = evaluationRepository.save(EvaluationFixture.fivePoints());
        long validId = evaluation.getId();
        int notChangedScore = 5;
        String notChangedContent = "서류가 인상 깊었습니다.";
        EvaluationUpdateRequest request = new EvaluationUpdateRequest(notChangedScore, notChangedContent);

        // when & then
        assertThatThrownBy(() -> evaluationService.update(request, validId))
                .isInstanceOf(EvaluationNoChangeException.class);
    }
}
