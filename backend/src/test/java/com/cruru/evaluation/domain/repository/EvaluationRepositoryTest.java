package com.cruru.evaluation.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.evaluation.domain.Evaluation;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.EvaluationFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("평가 레포지토리 테스트")
class EvaluationRepositoryTest extends RepositoryTest {

    @Autowired
    private EvaluationRepository evaluationRepository;

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
}
