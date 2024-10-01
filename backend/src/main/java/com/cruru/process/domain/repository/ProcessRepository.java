package com.cruru.process.domain.repository;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    List<Process> findAllByDashboardId(long dashboardId);

    int countByDashboard(Dashboard dashboard);

    List<Process> findAllByDashboard(Dashboard dashboard);

    @Query("""
           SELECT p FROM Process p 
           JOIN FETCH p.dashboard d
           JOIN FETCH d.club c
           JOIN FETCH c.member
           WHERE p.id = :id
           """)
    Optional<Process> findByIdFetchingMember(@Param("id") long id);
}
