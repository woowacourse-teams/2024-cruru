package com.cruru.applicant.domain;

import com.cruru.BaseEntity;
import com.cruru.applicant.exception.badrequest.ApplicantIllegalPhoneNumberException;
import com.cruru.applicant.exception.badrequest.ApplicantNameBlankException;
import com.cruru.applicant.exception.badrequest.ApplicantNameCharacterException;
import com.cruru.applicant.exception.badrequest.ApplicantNameLengthException;
import com.cruru.auth.util.SecureResource;
import com.cruru.dashboard.domain.Dashboard;
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
public class Applicant extends BaseEntity implements SecureResource {

    private static final int MAX_NAME_LENGTH = 32;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z\\s-]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(010)\\d{3,4}\\d{4}$|^(02|0[3-6][1-5])\\d{3,4}\\d{4}$");

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

    @Column(name = "is_rejected")
    private boolean isRejected;

    public Applicant(String name, String email, String phone, Process process) {
        validateName(name);
        validatePhone(phone);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.process = process;
        this.isRejected = false;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new ApplicantNameBlankException();
        }
        if (isLengthOutOfRange(name)) {
            throw new ApplicantNameLengthException(MAX_NAME_LENGTH, name.length());
        }
        if (containsInvalidCharacter(name)) {
            String invalidCharacters = Stream.of(NAME_PATTERN.matcher(name).replaceAll("").split(""))
                    .distinct()
                    .collect(Collectors.joining(", "));
            throw new ApplicantNameCharacterException(invalidCharacters);
        }
    }

    private void validatePhone(String phoneNumber) {
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new ApplicantIllegalPhoneNumberException();
        }
    }

    private boolean isLengthOutOfRange(String name) {
        return name.length() > MAX_NAME_LENGTH;
    }

    private boolean containsInvalidCharacter(String name) {
        return !NAME_PATTERN.matcher(name).matches();
    }

    public void updateInfo(String name, String email, String phone) {
        validateName(name);
        validatePhone(phone);
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
