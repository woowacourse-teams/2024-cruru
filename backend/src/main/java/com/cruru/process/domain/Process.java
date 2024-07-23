package com.cruru.process.domain;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.exception.ProcessBadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 32;
    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^[가-힣a-zA-Z0-9!@#$%^&*() ]{" + MIN_NAME_LENGTH + "," + MAX_NAME_LENGTH + "}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private Long id;

    private Integer sequence;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    public Process(int sequence, String name, String description, Dashboard dashboard) {
        validateName(name);
        this.sequence = sequence;
        this.name = name;
        this.description = description;
        this.dashboard = dashboard;
    }

    public void updateName(String name) {
        validateName(name);
        this.name = name;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    private void validateName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new ProcessBadRequestException(
                    String.format("동아리 이름이 %d글자 미만이거나 %d글자 초과, 혹은 '_'를 포함하고 있습니다.", MIN_NAME_LENGTH, MAX_NAME_LENGTH)
            );
        }
    }

    public void increaseSequenceNumber() {
        this.sequence++;
    }

    public boolean isSameSequence(int other) {
        return this.sequence == other;
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
