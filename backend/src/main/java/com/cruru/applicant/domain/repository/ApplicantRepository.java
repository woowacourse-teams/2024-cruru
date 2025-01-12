package com.cruru.applicant.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.applicant.domain.dto.ApplicantQuestionAnswerDto;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    List<Applicant> findAllByProcess(Process process);

    @EntityGraph(attributePaths = {"process.dashboard.club.member"})
    @Query("SELECT a FROM Applicant a WHERE a.id = :id")
    Optional<Applicant> findByIdFetchingMember(Long id);

    long countByProcess(Process process);

    @Query("""
           SELECT new com.cruru.applicant.domain.dto.ApplicantCard(
               a.id, a.name, a.createdDate, a.isRejected, COUNT(e), COALESCE(AVG(e.score), 0.00), a.process.id
           )
           FROM Applicant a
           LEFT JOIN Evaluation e ON e.applicant = a AND e.process = a.process
           WHERE a.process IN :processes
           GROUP BY a.id, a.name, a.createdDate, a.isRejected, a.process.id
           """)
    List<ApplicantCard> findApplicantCardsByProcesses(@Param("processes") List<Process> processes);

    @Query("""
           SELECT new com.cruru.applicant.domain.dto.ApplicantCard(
                      a.id, a.name, a.createdDate, a.isRejected, COUNT(e), COALESCE(AVG(e.score), 0.00), a.process.id
                  )
                  FROM Applicant a
                  LEFT JOIN Evaluation e ON e.applicant = a AND e.process = a.process
                  WHERE a.process = :process
                  GROUP BY a.id, a.name, a.createdDate, a.isRejected
           """)
    List<ApplicantCard> findApplicantCardsByProcess(@Param("process") Process process);

    @Query("SELECT a FROM Applicant a JOIN FETCH a.process p JOIN FETCH p.dashboard d WHERE d = :dashboard")
    List<Applicant> findAllByDashboard(@Param("dashboard") Dashboard dashboard);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           UPDATE Applicant a
           SET a.isRejected = :isRejected
           WHERE a.id in :applicantIds
           """)
    @Transactional
    void updateRejectedStatusForApplicants(List<Long> applicantIds, boolean isRejected);

    @Query("""
           SELECT new com.cruru.applicant.domain.dto.ApplicantQuestionAnswerDto(
               a.id, a.name, a.email, a.phone, a.createdDate,
               q.id, q.sequence, q.content,
               ans.content
           )
           FROM Applicant a
               JOIN a.process p
               JOIN p.dashboard d
               JOIN ApplyForm f ON f.dashboard = d
               LEFT JOIN Question q ON q.applyForm = f
               LEFT JOIN Answer ans ON ans.applicant = a AND ans.question = q
           WHERE f.id = :applyFormId
           ORDER BY a.id, q.sequence
           """)
    List<ApplicantQuestionAnswerDto> findApplicantQuestionAnswers(@Param("applyFormId") Long applyFormId);
}
