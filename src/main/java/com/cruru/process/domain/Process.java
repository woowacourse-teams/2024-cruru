package com.cruru.process.domain;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.exception.ProcessBadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Process {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9!@#$%^&*()]{1,32}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private Long id;

    private Integer sequence;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    public Process(int sequence, String name, String description, Dashboard dashboard) {
        validateName(name);
        this.sequence = sequence;
        this.name = name;
        this.description = description;
        this.dashboard = dashboard;
    }

    private void validateName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new ProcessBadRequestException();
        }
    }

    public void increaseSequenceNumber() {
        this.sequence++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Process process = (Process) o;
        return Objects.equals(id, process.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", sequence=" + sequence +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dashboard=" + dashboard +
                '}';
    }
}
