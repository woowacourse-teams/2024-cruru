package com.cruru.question.domain.repository;

import com.cruru.applicant.domain.Applicant;
import com.cruru.question.domain.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByApplicant(Applicant applicant);
}
