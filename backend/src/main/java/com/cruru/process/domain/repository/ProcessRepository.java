package com.cruru.process.domain.repository;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    List<Process> findAllByDashboardId(long dashboardId);

    int countByDashboard(Dashboard dashboard);

    List<Process> findAllByDashboard(Dashboard dashboard);

    @EntityGraph(attributePaths = {"dashboard.club.member"})
    @Query("SELECT p FROM Process p WHERE p.id = :id")
    @Override
    Optional<Process> findById(Long id);
}
