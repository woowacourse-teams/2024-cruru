package com.cruru.dashboard.domain;

import com.cruru.club.domain.Club;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dashboard_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    public Dashboard(String name, Club club) {
        this.name = name;
        this.club = club;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dashboard dashboard = (Dashboard) o;
        return Objects.equals(id, dashboard.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dashboard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", club=" + club +
                '}';
    }
}
