package com.cruru.process.domain.repository;

import com.cruru.process.domain.Process;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, Long> {

    List<Process> findAllByDashboardId(Long dashboardId);
}
