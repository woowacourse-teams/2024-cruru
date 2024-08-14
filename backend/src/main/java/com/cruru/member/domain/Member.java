package com.cruru.member.domain;

import static com.cruru.member.domain.MemberRole.CLUB_OWNER;

import com.cruru.BaseEntity;
import com.cruru.member.exception.badrequest.MemberIllegalPhoneNumberException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private static final Pattern VALID_PHONE_NUMBER_PATTERN = Pattern.compile(
            "^(010)\\d{3,4}\\d{4}$|^(02|0[3-6][1-5])\\d{3,4}\\d{4}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public Member(Long id, String email, String password, String phone) {
        this(email, password, phone);
        this.id = id;
    }

    public Member(String email, String password, String phone) {
        validatePhoneNumber(phone);
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = CLUB_OWNER;
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
        if (!(o instanceof Member member)) {
            return false;
        }
        return Objects.equals(id, member.id) && Objects.equals(email, member.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
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
