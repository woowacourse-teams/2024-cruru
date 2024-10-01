package com.cruru.dashboard.domain.repository;

import com.cruru.dashboard.domain.Dashboard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    @Query("""
           SELECT d FROM Dashboard d 
           JOIN FETCH d.club c 
           JOIN FETCH c.member 
           WHERE d.id = :id""")
    Optional<Dashboard> findByIdFetchingMember(@Param("id") long id);
}
