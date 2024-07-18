package com.cruru.answer.domain.repository;

import com.cruru.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
