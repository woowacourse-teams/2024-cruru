package com.cruru.question.domain.repository;

import com.cruru.question.domain.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByApplyFormId(long applyFormId);
}
