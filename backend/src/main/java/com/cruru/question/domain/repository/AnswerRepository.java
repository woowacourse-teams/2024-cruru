package com.cruru.question.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.question.domain.Answer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @EntityGraph(attributePaths = {"applicant.process.dashboard.club.member"})
    @Query("SELECT a FROM Answer a WHERE a.id = :id")
    @Override
    Optional<Answer> findById(Long id);

    @Query("SELECT a FROM Answer a JOIN FETCH a.question WHERE a.applicant = :applicant")
    List<Answer> findAllByApplicantWithQuestions(@Param("applicant") Applicant applicant);
}
