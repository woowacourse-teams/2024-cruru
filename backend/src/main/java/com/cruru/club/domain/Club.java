package com.cruru.club.domain;

import com.cruru.club.exception.ClubNameBlankException;
import com.cruru.club.exception.ClubNameCharacterException;
import com.cruru.club.exception.ClubNameLengthException;
import com.cruru.member.domain.Member;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Club {

    private static final int MAX_NAME_LENGTH = 32;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[^\\\\|]*$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Club(String name, Member member) {
        validateName(name);
        this.name = name;
        this.member = member;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new ClubNameBlankException();
        }
        if (isLengthOutOfRange(name)) {
            throw new ClubNameLengthException(MAX_NAME_LENGTH, name.length());
        }
        if (isContainingInvalidCharacter(name)) {
            String invalidCharacters = Stream.of(NAME_PATTERN.matcher(name).replaceAll("").split(""))
                    .distinct()
                    .collect(Collectors.joining(", "));
            throw new ClubNameCharacterException(invalidCharacters);
        }
    }

    private boolean isLengthOutOfRange(String name) {
        return name.length() > MAX_NAME_LENGTH;
    }

    private boolean isContainingInvalidCharacter(String name) {
        return !NAME_PATTERN.matcher(name).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Club club = (Club) o;
        return Objects.equals(id, club.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", member=" + member +
                '}';
    }
}
