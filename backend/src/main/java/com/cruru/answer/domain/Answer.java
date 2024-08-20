package com.cruru.answer.domain;

import com.cruru.answer.exception.AnswerContentLengthException;
import com.cruru.applicant.domain.Applicant;
import com.cruru.question.domain.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Answer {

    private static final int SHORT_ANSWER_MAX_LENGTH = 50;
    private static final int LONG_ANSWER_MAX_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    public Answer(String content, Question question, Applicant applicant) {
        validateContentLengthByQuestionType(content, question);
        this.content = content;
        this.question = question;
        this.applicant = applicant;
    }

    private void validateContentLengthByQuestionType(String content, Question question) {
        if (question.isShortAnswer()) {
            validateContentLength(SHORT_ANSWER_MAX_LENGTH, content.length());
        }

        if (question.isLongAnswer()) {
            validateContentLength(LONG_ANSWER_MAX_LENGTH, content.length());
        }
    }

    private void validateContentLength(int maxLength, int currentLength) {
        if (currentLength > maxLength) {
            throw new AnswerContentLengthException(maxLength, currentLength);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answer answer = (Answer) o;
        return Objects.equals(id, answer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", question=" + question +
                ", applicant=" + applicant +
                '}';
    }
}
