package com.cruru.applicant.domain;

import com.cruru.BaseEntity;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @Tsid
    @Column(name = "applicant_id")
    private Long id;

    private String name;

    private String email;

    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id")
    private Process process;

    @Column(name = "is_rejected")
    private boolean isRejected;

    public Applicant(String name, String email, String phone, Process process) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.process = process;
        this.isRejected = false;
    }

    public void updateInfo(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void updateProcess(Process process) {
        this.process = process;
    }

    public void unreject() {
        isRejected = false;
    }

    public void reject() {
        isRejected = true;
    }

    public boolean isApproved() {
        return process.isApproveType();
    }

    public boolean isRejected() {
        return isRejected;
    }

    public boolean isNotRejected() {
        return !isRejected;
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
                ", isRejected=" + isRejected +
                '}';
    }
}
