package com.cruru.question.domain.repository;

import static com.cruru.question.domain.QuestionType.DROPDOWN;
import static com.cruru.question.domain.QuestionType.SHORT_ANSWER;
import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.question.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("질문 레포지토리 테스트")
@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        questionRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 질문을 저장하면, 해당 ID의 질문은 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Question question = new Question(DROPDOWN, "성별", 0, null);
        Question saved = questionRepository.save(question);

        //when
        Question updateQuestion = new Question(saved.getId(), SHORT_ANSWER, "전공", 1, null);
        questionRepository.save(updateQuestion);

        //then
        Question findQuestion = questionRepository.findById(saved.getId()).get();
        assertThat(findQuestion.getContent()).isEqualTo("전공");
        assertThat(findQuestion.getSequence()).isEqualTo(1);
    }

    @DisplayName("ID가 없는 질문을 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        Question question1 = new Question(DROPDOWN, "성별", 0, null);
        Question question2 = new Question(SHORT_ANSWER, "전공", 1, null);

        //when
        Question savedQuestion1 = questionRepository.save(question1);
        Question savedQuestion2 = questionRepository.save(question2);

        //then
        assertThat(savedQuestion1.getId() + 1).isEqualTo(savedQuestion2.getId());
    }
}
