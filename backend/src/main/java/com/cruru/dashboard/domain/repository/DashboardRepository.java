package com.cruru.dashboard.domain.repository;

import com.cruru.club.domain.Club;
import com.cruru.dashboard.domain.Dashboard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    List<Dashboard> findAllByClub(Club club);
}
