package com.cruru.answer.domain.repository;

import com.cruru.answer.domain.Answer;
import com.cruru.applicant.domain.Applicant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAllByApplicant(Applicant applicant);
}
