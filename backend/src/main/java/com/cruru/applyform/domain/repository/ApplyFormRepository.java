package com.cruru.applyform.domain.repository;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.DashboardApplyFormDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplyFormRepository extends JpaRepository<ApplyForm, Long> {

    @EntityGraph(attributePaths = {"dashboard.club.member"})
    @Query("SELECT a FROM ApplyForm a WHERE a.id = :id")
    @Override
    Optional<ApplyForm> findById(Long id);

    Optional<ApplyForm> findByDashboard(Dashboard dashboard);

    @Query("""
               SELECT af FROM ApplyForm af
               JOIN FETCH af.dashboard d
               WHERE d.id = :dashboardId
           """)
    Optional<ApplyForm> findByDashboardId(long dashboardId);

    @Query("""
           SELECT new com.cruru.dashboard.domain.DashboardApplyFormDto(d, a) 
           FROM ApplyForm a 
           JOIN FETCH a.dashboard d
           JOIN FETCH d.club c 
           WHERE c.id = :clubId
           """)
    List<DashboardApplyFormDto> findAllByClub(@Param("clubId") Long clubId);
}
