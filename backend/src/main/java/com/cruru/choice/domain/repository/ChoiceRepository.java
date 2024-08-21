package com.cruru.choice.domain.repository;

import com.cruru.choice.domain.Choice;
import com.cruru.question.domain.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    List<Choice> findAllByQuestion(Question question);

    void deleteAllByQuestion(Question question);
}
