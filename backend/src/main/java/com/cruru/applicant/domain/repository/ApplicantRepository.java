package com.cruru.applicant.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.process.domain.Process;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    List<Applicant> findAllByProcess(Process process);

    long countByProcess(Process process);

    @Query("""
                  SELECT new com.cruru.applicant.domain.dto.ApplicantCard( 
                      a.id, a.name, a.createdDate, a.isRejected, COUNT(e), COALESCE(AVG(e.score), 0.00) 
                  )
                  FROM Applicant a
                  LEFT JOIN Evaluation e ON e.applicant = a
                  WHERE a.process = :process 
                  GROUP BY a.id, a.name, a.createdDate, a.isRejected
           """)
    List<ApplicantCard> findApplicantCardsByProcess(@Param("process") Process process);
}
