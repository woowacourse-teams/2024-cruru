package com.cruru.question.service.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.choice.controller.dto.ChoiceCreateRequest;
import com.cruru.choice.domain.Choice;
import com.cruru.choice.domain.repository.ChoiceRepository;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.controller.dto.QuestionUpdateRequests;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ChoiceFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("질문 파사드 테스트")
class QuestionFacadeTest extends ServiceTest {

    @Autowired
    private QuestionFacade questionFacade;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @DisplayName("질문을 수정한다.")
    @Test
    void update() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend());
        Question question = questionRepository.save(QuestionFixture.multipleChoiceType(applyForm));
        choiceRepository.save(ChoiceFixture.first(question));
        choiceRepository.save(ChoiceFixture.second(question));

        Question newQuestion = QuestionFixture.singleChoiceType(applyForm);
        Choice newChoice = ChoiceFixture.third(question);

        QuestionUpdateRequests questionUpdateRequest = new QuestionUpdateRequests(List.of(
                new QuestionCreateRequest(
                        newQuestion.getQuestionType().name(),
                        newQuestion.getContent(),
                        List.of(new ChoiceCreateRequest(
                                newChoice.getContent(),
                                newChoice.getSequence()
                        )),
                        newQuestion.getSequence(),
                        newQuestion.getRequired()
                )));

        // when
        questionFacade.update(questionUpdateRequest, applyForm.getId());

        // then
        List<Question> actualQuestions = questionRepository.findAllByApplyForm(applyForm);
        Question actualQuestion = actualQuestions.get(0);
        List<Choice> actualChoices = choiceRepository.findAllByQuestion(actualQuestion);
        Choice actualChoice = actualChoices.get(0);
        assertAll(
                () -> assertThat(actualQuestions).hasSize(1),
                () -> assertThat(actualQuestion.getQuestionType()).isEqualTo(newQuestion.getQuestionType()),
                () -> assertThat(actualQuestion.getContent()).isEqualTo(newQuestion.getContent()),
                () -> assertThat(actualQuestion.getSequence()).isEqualTo(newQuestion.getSequence()),
                () -> assertThat(actualQuestion.getRequired()).isEqualTo(newQuestion.getRequired()),

                () -> assertThat(actualChoices).hasSize(1),
                () -> assertThat(actualChoice.getContent()).isEqualTo(newChoice.getContent()),
                () -> assertThat(actualChoice.getSequence()).isEqualTo(newChoice.getSequence())
        );
    }
}
