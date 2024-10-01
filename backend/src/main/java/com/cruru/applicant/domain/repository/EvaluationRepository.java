package com.cruru.applicant.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.process.domain.Process;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findAllByProcessAndApplicant(Process process, Applicant applicant);

    void deleteByProcessId(long processId);

    @Query("""
           SELECT e FROM Evaluation e 
           JOIN FETCH e.process p 
           JOIN FETCH p.dashboard d
           JOIN FETCH d.club c
           JOIN FETCH c.member
           WHERE e.id = :id""")
    Optional<Evaluation> findByIdFetchingMember(@Param("id") long id);
}
