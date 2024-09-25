package com.cruru.dashboard.domain.repository;

import com.cruru.dashboard.domain.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

}
