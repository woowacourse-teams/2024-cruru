package com.cruru.question.domain.repository;

import com.cruru.question.domain.Choice;
import com.cruru.question.domain.Question;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    @EntityGraph(attributePaths = {"question.applicant.process.dashboard.club.member"})
    @Query("SELECT c FROM Choice c WHERE c.id = :id")
    Optional<Choice> findByIdFetchingMember(long id);

    List<Choice> findAllByQuestion(Question question);

    void deleteAllByQuestion(Question question);
}
