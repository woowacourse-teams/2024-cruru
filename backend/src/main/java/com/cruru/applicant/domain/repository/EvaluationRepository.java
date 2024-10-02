package com.cruru.applicant.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.process.domain.Process;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findAllByProcessAndApplicant(Process process, Applicant applicant);

    void deleteByProcessId(long processId);

    @EntityGraph(attributePaths = {"applicant.process.dashboard.club.member"})
    @Query("SELECT e FROM Evaluation e WHERE e.id = :id")
    @Override
    Optional<Evaluation> findById(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM Evaluation e WHERE e.process IN :processes")
    void deleteAllByProcesses(@Param("processes") List<Process> processes);
}
