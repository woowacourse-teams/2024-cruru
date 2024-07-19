package com.cruru.answer.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.answer.domain.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("주관식 답변 레포지토리 테스트")
@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        answerRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 답변을 저장하면, 해당 ID의 답변은 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Answer answer = new Answer("체육 전공입니다.", null, null);
        Answer saved = answerRepository.save(answer);

        //when
        Answer updateAnswer = new Answer(saved.getId(), "음악 전공입니다.", null, null);
        answerRepository.save(updateAnswer);

        //then
        Answer foundAnswer = answerRepository.findById(saved.getId()).get();
        assertThat(foundAnswer.getContent()).isEqualTo("음악 전공입니다.");
    }

    @DisplayName("ID가 없는 답변을 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        Answer answer1 = new Answer("체육 전공입니다.", null, null);
        Answer answer2 = new Answer("음악 전공입니다.", null, null);

        //when
        Answer savedAnswer1 = answerRepository.save(answer1);
        Answer savedAnswer2 = answerRepository.save(answer2);

        //then
        assertThat(savedAnswer1.getId() + 1).isEqualTo(savedAnswer2.getId());
    }
}
