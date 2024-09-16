package com.cruru.email.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.email.exception.EmailContentLengthException;
import com.cruru.email.exception.EmailSubjectLengthException;
import com.cruru.util.fixture.EmailFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("이메일 발송 내역 도메인 테스트")
class EmailTest {

    @DisplayName("이메일 제목이 998자를 초과하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"012", "가나다", "abc"})
    void invalidEmailSubjectLength(String subject) {
        // given
        int repeatCount = 333;
        StringBuilder stringBuilder = new StringBuilder(subject.length() * repeatCount);
        for (int i = 0; i < repeatCount; i++) {
            stringBuilder.append(subject);
        }
        String invalidSubject = stringBuilder.toString();
        String content = EmailFixture.APPROVE_CONTENT;

        // when&then
        assertThatThrownBy(() -> new Email(null, null, invalidSubject, content))
                .isInstanceOf(EmailSubjectLengthException.class);
    }

    @DisplayName("이메일 본문이 10,000자를 초과하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0123456789", "가나다라마바사아자차", "abcdefghij"})
    void invalidEmailContentLength(String content) {
        // given
        int repeatCount = 1000;
        StringBuilder stringBuilder = new StringBuilder(content.length() * repeatCount);
        for (int i = 0; i < repeatCount; i++) {
            stringBuilder.append(content);
        }
        String subject = EmailFixture.SUBJECT;
        String invalidContent = stringBuilder.append("!").toString();

        // when&then
        assertThatThrownBy(() -> new Email(null, null, subject, invalidContent))
                .isInstanceOf(EmailContentLengthException.class);
    }
}
