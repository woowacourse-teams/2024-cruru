package com.cruru.evaluation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.evaluation.controller.dto.EvaluationCreateRequest;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.evaluation.domain.repository.EvaluationRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("평가 서비스 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EvaluationServiceTest {

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
        evaluationRepository.deleteAll();
        applicantRepository.deleteAll();
        processRepository.deleteAll();

        process = processRepository.save(new Process(0, "서류", "서류", null));

        applicant = applicantRepository.save(
                new Applicant(1L, "초코칩", "dev.chocochip@gmail.com", "01012345678", process)
        );
    }

    @DisplayName("새로운 평가를 생성한다.")
    @Test
    void create() {
        // given
        int score = 4;
        String content = "서류가 인상적입니다.";

        // when
        EvaluationCreateRequest request = new EvaluationCreateRequest(score, content);
        long evaluationId = evaluationService.create(request, process.getId(), applicant.getId());

        // then
        Optional<Evaluation> evaluation = evaluationRepository.findById(evaluationId);

        assertAll(
                () -> assertThat(evaluation).isNotNull(),
                () -> assertThat(evaluation.get().getScore()).isEqualTo(score),
                () -> assertThat(evaluation.get().getContent()).isEqualTo(content)
        );
    }
}
