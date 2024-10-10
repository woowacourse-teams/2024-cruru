package com.cruru.question.domain.repository;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.question.domain.Question;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @EntityGraph(attributePaths = {"applyForm.dashboard.club.member"})
    @Query("SELECT q FROM Question q WHERE q.id = :id")
    Optional<Question> findByIdFetchingMember(Long id);

    List<Question> findAllByApplyForm(ApplyForm applyForm);
}
