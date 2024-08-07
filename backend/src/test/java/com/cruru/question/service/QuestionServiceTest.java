package com.cruru.question.service;

import static com.cruru.util.fixture.ApplyFormFixture.createBackendApplyForm;
import static com.cruru.util.fixture.QuestionFixture.createLongAnswerQuestion;
import static com.cruru.util.fixture.QuestionFixture.createShortAnswerQuestion;
import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("질문 서비스 테스트")
class QuestionServiceTest extends ServiceTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private QuestionService questionService;

    @DisplayName("질문 일괄 생성에 성공한다.")
    @Test
    void createAll() {
        // given
        ApplyForm applyForm = applyFormRepository.save(createBackendApplyForm(null));
        Question question1 = createShortAnswerQuestion(applyForm);
        Question question2 = createLongAnswerQuestion(applyForm);
        List<QuestionCreateRequest> requests = List.of(
                new QuestionCreateRequest(
                        question1.getQuestionType().toString(),
                        question1.getContent(),
                        question1.getDescription(),
                        null,
                        0,
                        question1.getRequired()
                ),
                new QuestionCreateRequest(
                        question2.getQuestionType().toString(),
                        question2.getContent(),
                        question2.getDescription(),
                        null,
                        1,
                        question2.getRequired()
                )
        );

        // when
        questionService.createAll(requests, applyForm);

        // then
        List<Question> questions = questionRepository.findAllByApplyFormId(applyForm.getId());
        assertThat(questions).hasSize(requests.size());
    }

    @DisplayName("질문 생성에 성공한다.")
    @Test
    void create() {
        // given
        ApplyForm applyForm = applyFormRepository.save(createBackendApplyForm(null));
        Question question1 = createShortAnswerQuestion(applyForm);
        QuestionCreateRequest request = new QuestionCreateRequest(
                question1.getQuestionType().toString(),
                question1.getContent(),
                question1.getDescription(),
                null,
                0,
                question1.getRequired()
        );

        // when
        Question question = questionService.create(request, applyForm);

        // then
        List<Question> questions = questionRepository.findAllByApplyFormId(applyForm.getId());
        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isEqualTo(question);
    }
}
