package com.cruru.applyform.domain;

import com.cruru.BaseEntity;
import com.cruru.applyform.exception.badrequest.StartDateAfterEndDateException;
import com.cruru.dashboard.domain.Dashboard;
import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplyForm extends BaseEntity {

    @Id
    @Tsid
    @Column(name = "apply_form_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Setter
    private String url;

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
            String url,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Dashboard dashboard
    ) {
        validateDate(startDate, endDate);
        this.title = title;
        this.description = description;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dashboard = dashboard;
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new StartDateAfterEndDateException(startDate, endDate);
        }
    }

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
                ", url='" + url + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", dashboard=" + dashboard +
                '}';
    }
}
