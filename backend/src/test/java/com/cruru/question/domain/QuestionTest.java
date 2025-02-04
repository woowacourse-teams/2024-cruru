package com.cruru.question.domain;

import static com.cruru.question.domain.QuestionType.LONG_ANSWER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.question.exception.QuestionDescriptionLengthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("질문 도메인 테스트")
class QuestionTest {

    @DisplayName("질문 보조 설명의 길이가 300자를 초과하면 예외가 발생한다.")
    @Test
    void createQuestion_invalidDescriptionLength() {
        // given
        int repeatCount = 10;
        String invalidDescription = "ThisTextIsGreaterThan300Chars!!";

        // when&then
        assertThatThrownBy(() -> new Question(
                LONG_ANSWER,
                "주관식 장문형",
                invalidDescription.repeat(repeatCount),
                2,
                false,
                null
        )).isInstanceOf(QuestionDescriptionLengthException.class);
    }
}
