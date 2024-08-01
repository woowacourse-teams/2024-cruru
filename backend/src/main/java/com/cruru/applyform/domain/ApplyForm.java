package com.cruru.applyform.domain;

import com.cruru.BaseEntity;
import com.cruru.dashboard.domain.Dashboard;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ApplyForm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_form_id")
    private Long id;

    private String title;

    private String description;

    @Setter
    private String url;

    @Column(name = "open_date")
    private LocalDateTime openDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @OneToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    public ApplyForm(
            String title,
            String description,
            String url,
            LocalDateTime openDate,
            LocalDateTime dueDate,
            Dashboard dashboard
    ) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.openDate = openDate;
        this.dueDate = dueDate;
        this.dashboard = dashboard;
    }

    public ApplyForm(
            String title,
            String description,
            LocalDateTime openDate,
            LocalDateTime dueDate,
            Dashboard dashboard
    ) {
        this.title = title;
        this.description = description;
        this.openDate = openDate;
        this.dueDate = dueDate;
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
                "dashboard=" + dashboard +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", openDate=" + openDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
