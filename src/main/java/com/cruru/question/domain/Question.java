package com.cruru.question.domain;

import com.cruru.dashboard.domain.Dashboard;
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
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    private String content;

    private int sequence;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    public Question(String content, int sequence, Dashboard dashboard) {
        this.content = content;
        this.sequence = sequence;
        this.dashboard = dashboard;
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
                ", content='" + content + '\'' +
                ", sequence=" + sequence +
                ", dashboard=" + dashboard +
                '}';
    }
}
