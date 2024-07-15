package com.cruru.process.domain.repository;

import com.cruru.process.domain.Process;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    List<Process> findAllByDashboardId(Long dashboardId);
}
