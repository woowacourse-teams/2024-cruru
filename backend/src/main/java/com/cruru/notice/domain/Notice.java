package com.cruru.notice.domain;

import com.cruru.BaseEntity;
import com.cruru.applicant.domain.Applicant;
import com.cruru.club.domain.Club;
import com.cruru.notice.exception.EmailSubjectLengthException;
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
public class Notice extends BaseEntity {

    private static final int EMAIL_SUBJECT_MAX_LENGTH = 998;
    private static final int EMAIL_TEXT_MAX_LENGTH = 10_000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club from; // 발신자 정보(동아리)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Applicant to;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(columnDefinition = "varchar")
    @Enumerated(EnumType.STRING)
    private NoticeType type;

    public Notice(Club from, Applicant to, String subject, String text, NoticeType type) {
        validateSubjectLength(subject);
        validateTextLength(text);
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.type = type;
    }

    private void validateSubjectLength(String subject) {
        if (subject.length() > EMAIL_SUBJECT_MAX_LENGTH) {
            throw new EmailSubjectLengthException(EMAIL_SUBJECT_MAX_LENGTH, subject.length());
        }
    }

    private void validateTextLength(String text) {
        if (text.length() > EMAIL_TEXT_MAX_LENGTH) {
            throw new EmailSubjectLengthException(EMAIL_TEXT_MAX_LENGTH, text.length());
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
        Notice notice = (Notice) o;
        return Objects.equals(id, notice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", type=" + type +
                '}';
    }
}
