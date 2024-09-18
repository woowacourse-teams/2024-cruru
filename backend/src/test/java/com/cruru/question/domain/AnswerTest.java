package com.cruru.question.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.question.exception.AnswerContentLengthException;
import com.cruru.util.fixture.QuestionFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("답변 도메인 테스트")
class AnswerTest {

    @DisplayName("질문 형식이 단답형인 경우, 답변 내용의 길이가 50자를 초과하면 예외가 발생한다.")
    @Test
    void createShortAnswer_invalidContentLength() {
        // given
        String invalidContent = "ThisTextIsGreaterThan50CharactersSoExceptionOccurs!";
        Question question = QuestionFixture.shortAnswerType(null);

        // when&then
        assertThatThrownBy(() -> new Answer(invalidContent, question, null))
                .isInstanceOf(AnswerContentLengthException.class);
    }

    @DisplayName("질문 형식이 장문형인 경우, 답변 내용의 길이가 1,000자를 초과하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0123456789", "가나다라마바사아자차", "abcdefghij"})
    void createLongAnswer_invalidContentLength(String content) {
        // given
        int repeatCount = 100;
        StringBuilder stringBuilder = new StringBuilder(content.length() * repeatCount);
        for (int i = 0; i < repeatCount; i++) {
            stringBuilder.append(content);
        }
        String invalidContent = stringBuilder.append("!").toString();
        Question question = QuestionFixture.longAnswerType(null);

        // when&then
        assertThatThrownBy(() -> new Answer(invalidContent, question, null))
                .isInstanceOf(AnswerContentLengthException.class);
    }
}
