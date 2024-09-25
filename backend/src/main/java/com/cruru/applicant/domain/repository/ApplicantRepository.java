package com.cruru.applicant.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    List<Applicant> findAllByProcess(Process process);

    long countByProcess(Process process);

    @Query("SELECT a FROM Applicant a JOIN FETCH a.process p JOIN FETCH p.dashboard d WHERE d = :dashboard")
    List<Applicant> findAllByDashboard(@Param("dashboard") Dashboard dashboard);
}
