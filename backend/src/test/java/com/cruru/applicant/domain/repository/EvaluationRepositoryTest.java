package com.cruru.applicant.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.Evaluation;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.ProcessFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("평가 레포지토리 테스트")
class EvaluationRepositoryTest extends RepositoryTest {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ProcessRepository processRepository;

    @BeforeEach
    void setUp() {
        evaluationRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 프로세스를 저장하면, 해당 ID의 프로세스는 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Evaluation evaluation = EvaluationFixture.fivePoints();
        Evaluation saved = evaluationRepository.save(evaluation);

        //when
        Evaluation updatedEvaluation = new Evaluation(evaluation.getId(), 5, "포트폴리오가 인상 깊었습니다.", null, null);
        evaluationRepository.save(updatedEvaluation);

        //then
        Evaluation findEvaluation = evaluationRepository.findById(saved.getId()).get();
        assertThat(findEvaluation.getScore()).isEqualTo(5);
        assertThat(findEvaluation.getContent()).isEqualTo("포트폴리오가 인상 깊었습니다.");
    }

    @DisplayName("ID가 없는 프로세스를 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        Evaluation evaluation1 = EvaluationFixture.fivePoints();
        Evaluation evaluation2 = EvaluationFixture.fourPoints();

        //when
        Evaluation savedEvaluation1 = evaluationRepository.save(evaluation1);
        Evaluation savedEvaluation2 = evaluationRepository.save(evaluation2);

        //then
        assertThat(savedEvaluation1.getId() + 1).isEqualTo(savedEvaluation2.getId());
    }

    @DisplayName("입력된 프로세스 목록에 해당하는 평가들을 삭제한다.")
    @Test
    void deleteAllByProcesses() {
        // given
        com.cruru.process.domain.Process process1 = processRepository.save(ProcessFixture.applyType());
        com.cruru.process.domain.Process process2 = processRepository.save(ProcessFixture.interview(null));
        com.cruru.process.domain.Process process3 = processRepository.save(ProcessFixture.approveType());
        List<Process> processes = List.of(process1, process2);

        Evaluation evaluation1 = evaluationRepository.save(EvaluationFixture.fivePoints(process1, null));
        Evaluation evaluation2 = evaluationRepository.save(EvaluationFixture.fivePoints(process2, null));
        Evaluation evaluation3 = evaluationRepository.save(EvaluationFixture.fivePoints(process3, null));

        // when
        evaluationRepository.deleteAllByProcesses(processes);

        // then
        assertThat(evaluationRepository.findAll()).contains(evaluation3)
                .doesNotContain(evaluation1, evaluation2);
    }
}
