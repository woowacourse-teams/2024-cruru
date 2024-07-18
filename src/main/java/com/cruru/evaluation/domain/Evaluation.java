package com.cruru.evaluation.domain;

import com.cruru.BaseEntity;
import com.cruru.applicant.domain.Applicant;
import com.cruru.evaluation.exception.EvaluationBadRequestException;
import com.cruru.process.domain.Process;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Evaluation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long id;

    private Integer score;

    private String content;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private Process process;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    public Evaluation(int score, String content, Process process, Applicant applicant) {
        validateScore(score);
        this.score = score;
        this.content = content;
        this.process = process;
        this.applicant = applicant;
    }

    private void validateScore(int score) {
        if (score <= 0 || score > 5) {
            throw new EvaluationBadRequestException();
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
        Evaluation that = (Evaluation) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", score=" + score +
                ", content='" + content + '\'' +
                ", process=" + process +
                ", applicant=" + applicant +
                '}';
    }
}
