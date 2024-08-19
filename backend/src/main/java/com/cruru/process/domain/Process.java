package com.cruru.process.domain;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.exception.badrequest.ProcessNameBlankException;
import com.cruru.process.exception.badrequest.ProcessNameCharacterException;
import com.cruru.process.exception.badrequest.ProcessNameLengthException;
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
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Process {

    private static final int MAX_NAME_LENGTH = 32;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[^\\\\|]*$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private Long id;

    private Integer sequence;

    private String name;

    private String description;

    @Column(columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private ProcessType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    public Process(int sequence, String name, String description, ProcessType type, Dashboard dashboard) {
        validateName(name);
        this.sequence = sequence;
        this.name = name;
        this.description = description;
        this.type = type;
        this.dashboard = dashboard;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new ProcessNameBlankException();
        }
        if (isLengthOutOfRange(name)) {
            throw new ProcessNameLengthException(MAX_NAME_LENGTH, name.length());
        }
        if (isContainingInvalidCharacter(name)) {
            String invalidCharacters = Stream.of(NAME_PATTERN.matcher(name).replaceAll("").split(""))
                    .distinct()
                    .collect(Collectors.joining(", "));
            throw new ProcessNameCharacterException(invalidCharacters);
        }
    }

    private boolean isLengthOutOfRange(String name) {
        return name.length() > MAX_NAME_LENGTH;
    }

    private boolean isContainingInvalidCharacter(String name) {
        return !NAME_PATTERN.matcher(name).matches();
    }

    public boolean isApplyType() {
        return type == ProcessType.APPLY;
    }

    public boolean isApproveType() {
        return type == ProcessType.APPROVE;
    }

    public boolean isFixed() {
        return type.isFixed();
    }

    public void updateName(String name) {
        validateName(name);
        this.name = name;
    }

    public void updateDescription(String description) {
        this.description = description;
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
                ", type=" + type +
                ", dashboard=" + dashboard +
                '}';
    }
}
