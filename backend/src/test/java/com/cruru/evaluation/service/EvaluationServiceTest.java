package com.cruru.evaluation.service;

import static com.cruru.fixture.ApplicantFixture.createApplicantDobby;
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
import java.util.List;
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
        process = processRepository.save(new Process(0, "서류", "서류", null));

        applicant = applicantRepository.save(createApplicantDobby(process));
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
        List<Evaluation> evaluations = evaluationRepository.findAllByProcessIdAndApplicantId(
                process.getId(), applicant.getId());
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
}
