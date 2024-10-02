package com.cruru.dashboard.domain.repository;

import com.cruru.dashboard.domain.Dashboard;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    @EntityGraph(attributePaths = {"club.member"})
    @Query("SELECT d FROM Dashboard d WHERE d.id = :id")
    @Override
    Optional<Dashboard> findById(Long id);
}
