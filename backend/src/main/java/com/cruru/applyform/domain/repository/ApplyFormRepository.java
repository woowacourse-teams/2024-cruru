package com.cruru.applyform.domain.repository;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.dashboard.domain.Dashboard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplyFormRepository extends JpaRepository<ApplyForm, Long> {

    Optional<ApplyForm> findByDashboard(Dashboard dashboard);

    @Query("""
               SELECT af FROM ApplyForm af
               JOIN FETCH af.dashboard d
               WHERE d.id = :dashboardId
           """)
    Optional<ApplyForm> findByDashboardId(long dashboardId);
}
