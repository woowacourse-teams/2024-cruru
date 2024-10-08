package com.cruru.question.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.question.domain.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM Answer a JOIN FETCH a.question WHERE a.applicant = :applicant")
    List<Answer> findAllByApplicantWithQuestions(@Param("applicant") Applicant applicant);
}
