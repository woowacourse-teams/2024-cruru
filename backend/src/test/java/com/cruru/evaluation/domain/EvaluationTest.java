package com.cruru.evaluation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.evaluation.exception.badrequest.EvaluationScoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("평가 도메인 테스트")
class EvaluationTest {

    @DisplayName("잘못된 평가 점수로 생성 시 예외가 발생한다.")
    @ValueSource(ints = {-1, 0, 6})
    @ParameterizedTest
    void invalidEvaluationScore(int invalidScore) {
        // given
        String content = "포트폴리오가 인상적입니다.";

        // when&then
        assertThatThrownBy(() -> new Evaluation(invalidScore, content, null, null))
                .isInstanceOf(EvaluationScoreException.class);
    }
}
