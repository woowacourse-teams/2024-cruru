package com.cruru.chosenresponse.domain;

import com.cruru.applicant.domain.Applicant;
import com.cruru.choice.domain.Choice;
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
public class ChosenResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "choice_id")
    private Choice choice;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private Applicant applicant;

    public ChosenResponse(Choice choice, Applicant applicant) {
        this.choice = choice;
        this.applicant = applicant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChosenResponse that = (ChosenResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ChosenResponse{" +
                "id=" + id +
                ", choice=" + choice +
                ", applicant=" + applicant +
                '}';
    }
}
