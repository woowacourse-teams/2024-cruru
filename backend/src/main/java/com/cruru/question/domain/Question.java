package com.cruru.question.domain;

import com.cruru.applyform.domain.ApplyForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column(columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    private String content;

    private Integer sequence;

    private Boolean required;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_form_id")
    private ApplyForm applyForm;

    public Question(
            QuestionType questionType,
            String content,
            Integer sequence,
            Boolean required,
            ApplyForm applyForm
    ) {
        this.questionType = questionType;
        this.content = content;
        this.sequence = sequence;
        this.required = required;
        this.applyForm = applyForm;
    }

    public boolean hasChoice() {
        return questionType.hasChoice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionType=" + questionType +
                ", content='" + content + '\'' +
                ", sequence=" + sequence +
                ", applyForm=" + applyForm +
                '}';
    }
}
