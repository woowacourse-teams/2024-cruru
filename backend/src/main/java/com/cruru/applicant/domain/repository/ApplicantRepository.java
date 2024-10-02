package com.cruru.applicant.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    List<Applicant> findAllByProcess(Process process);

    // TODO: 이유는 모르겠지만 entityGraph가 적용 안됨.
    @Query("""
           SELECT a FROM Applicant a
           LEFT JOIN FETCH a.process p
           LEFT JOIN FETCH p.dashboard d
           LEFT JOIN FETCH d.club c
           LEFT JOIN FETCH c.member m
           WHERE a.id = :id
           """)
    Optional<Applicant> findByIdFetchingMember(long id);

    long countByProcess(Process process);

    @Query("""
           SELECT new com.cruru.applicant.domain.dto.ApplicantCard( 
               a.id, a.name, a.createdDate, a.isRejected, COUNT(e), COALESCE(AVG(e.score), 0.00), a.process.id
           )
           FROM Applicant a
           LEFT JOIN Evaluation e ON e.applicant = a
           WHERE a.process IN :processes
           GROUP BY a.id, a.name, a.createdDate, a.isRejected, a.process.id
           """)
    List<ApplicantCard> findApplicantCardsByProcesses(@Param("processes") List<Process> processes);

    @Query("""
           SELECT new com.cruru.applicant.domain.dto.ApplicantCard(
                      a.id, a.name, a.createdDate, a.isRejected, COUNT(e), COALESCE(AVG(e.score), 0.00), a.process.id
                  )
                  FROM Applicant a
                  LEFT JOIN Evaluation e ON e.applicant = a
                  WHERE a.process = :process
                  GROUP BY a.id, a.name, a.createdDate, a.isRejected
           """)
    List<ApplicantCard> findApplicantCardsByProcess(@Param("process") Process process);

    @Query("SELECT a FROM Applicant a JOIN FETCH a.process p JOIN FETCH p.dashboard d WHERE d = :dashboard")
    List<Applicant> findAllByDashboard(@Param("dashboard") Dashboard dashboard);
}
