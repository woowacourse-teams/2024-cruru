package com.cruru.applicant.domain;

import com.cruru.BaseEntity;
import com.cruru.applicant.exception.badrequest.EvaluationScoreException;
import com.cruru.auth.util.SecureResource;
import com.cruru.member.domain.Member;
import com.cruru.process.domain.Process;
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
public class Evaluation extends BaseEntity implements SecureResource {

    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long id;

    private String evaluator;

    private Integer score;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private Process process;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    public Evaluation(String evaluator, int score, String content, Process process, Applicant applicant) {
        validateScore(score);
        this.evaluator = evaluator;
        this.score = score;
        this.content = content;
        this.process = process;
        this.applicant = applicant;
    }

    private void validateScore(int score) {
        if (isOutOfRange(score)) {
            throw new EvaluationScoreException(MIN_SCORE, MAX_SCORE, score);
        }
    }

    private boolean isOutOfRange(int score) {
        return score < MIN_SCORE || score > MAX_SCORE;
    }

    @Override
    public boolean isAuthorizedBy(Member member) {
        return process.isAuthorizedBy(member);
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
