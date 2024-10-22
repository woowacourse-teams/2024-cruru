package com.cruru.applyform.domain;

import com.cruru.BaseEntity;
import com.cruru.applyform.exception.badrequest.StartDateAfterEndDateException;
import com.cruru.auth.util.SecureResource;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.global.util.TsidSupplier;
import com.cruru.member.domain.Member;
import io.hypersistence.tsid.TSID;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplyForm extends BaseEntity implements SecureResource {

    @Id
    @Tsid(TsidSupplier.class)
    @Column(name = "apply_form_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    public ApplyForm(
            String title,
            String description,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Dashboard dashboard
    ) {
        validateDate(startDate, endDate);
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dashboard = dashboard;
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        validateStartDateBeforeEndDate(startDate, endDate);
    }

    private void validateStartDateBeforeEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new StartDateAfterEndDateException(startDate, endDate);
        }
    }

    public boolean hasStarted(LocalDate now) {
        return !startDate.toLocalDate().isAfter(now);
    }

    @Override
    public boolean isAuthorizedBy(Member member) {
        return dashboard.isAuthorizedBy(member);
    }

    public String toStringTsid() {
        return TSID.from(this.id).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplyForm applyForm)) {
            return false;
        }
        return Objects.equals(id, applyForm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApplyForm{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", dashboard=" + dashboard +
                '}';
    }
}
