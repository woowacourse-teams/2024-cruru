package com.cruru.dashboard.domain.repository;

import com.cruru.dashboard.domain.Dashboard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    
    @Query("SELECT d FROM Dashboard d JOIN FETCH d.club c WHERE c.id = :clubId")
    List<Dashboard> findAllByClubWithJoinFetch(@Param("clubId") Long clubId);
}
