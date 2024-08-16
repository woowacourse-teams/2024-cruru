package com.cruru.applicant.domain;

import static com.cruru.applicant.domain.ApplicantState.APPROVED;
import static com.cruru.applicant.domain.ApplicantState.PENDING;
import static com.cruru.applicant.domain.ApplicantState.REJECTED;

import com.cruru.BaseEntity;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
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
public class Applicant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_id")
    private Long id;

    private String name;

    private String email;

    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private Process process;

    @Column(columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private ApplicantState state;

    public Applicant(String name, String email, String phone, Process process) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.process = process;
        this.state = PENDING;
    }

    public void updateInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void updateProcess(Process process) {
        this.process = process;
    }

    public void approve() {
        this.state = APPROVED;
    }

    public void unreject() {
        this.state = PENDING;
    }

    public void reject() {
        this.state = REJECTED;
    }

    public boolean isApproved() {
        return this.state == APPROVED;
    }

    public boolean isPending() {
        return this.state == PENDING;
    }

    public boolean isRejected() {
        return this.state == REJECTED;
    }

    public boolean isNotRejected() {
        return this.state != REJECTED;
    }

    public Dashboard getDashboard() {
        return process.getDashboard();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Applicant applicant = (Applicant) o;
        return Objects.equals(id, applicant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", process=" + process +
                ", state=" + state +
                '}';
    }
}
