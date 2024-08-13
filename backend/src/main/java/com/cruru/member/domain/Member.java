package com.cruru.member.domain;

import com.cruru.BaseEntity;
import com.cruru.member.exception.badrequest.MemberIllegalPasswordException;
import com.cruru.member.exception.badrequest.MemberIllegalPhoneNumberException;
import com.cruru.member.exception.badrequest.MemberPasswordLengthException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Member extends BaseEntity {

    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 32;
    private static final Pattern VALID_PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).*$");
    private static final Pattern VALID_PHONE_NUMBER_PATTERN = Pattern.compile(
            "^(01[0|1|6|7|8|9])\\d{3,4}\\d{4}$|^(02|0[3-6][1-5])\\d{3,4}\\d{4}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    public Member(String email, String password, String phone) {
        validatePassword(password);
        validatePhoneNumber(phone);
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    private void validatePassword(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            throw new MemberPasswordLengthException(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        }
        if (!VALID_PASSWORD_PATTERN.matcher(password).matches()) {
            throw new MemberIllegalPasswordException();
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!VALID_PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            throw new MemberIllegalPhoneNumberException();
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
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phone + '\'' +
                '}';
    }
}
