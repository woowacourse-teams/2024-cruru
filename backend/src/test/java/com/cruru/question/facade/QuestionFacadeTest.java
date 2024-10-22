package com.cruru.question.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.question.controller.request.ChoiceCreateRequest;
import com.cruru.question.controller.request.QuestionCreateRequest;
import com.cruru.question.controller.request.QuestionUpdateRequests;
import com.cruru.question.domain.Choice;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.ChoiceRepository;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.question.exception.QuestionUnmodifiableException;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ChoiceFixture;
import com.cruru.util.fixture.QuestionFixture;
import io.hypersistence.tsid.TSID;
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
    void updateById() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.notStarted());
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
                        newQuestion.isRequired()
                )));

        // when
        questionFacade.update(questionUpdateRequest, applyForm.toStringTsid());

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
                () -> assertThat(actualQuestion.isRequired()).isEqualTo(newQuestion.isRequired()),

                () -> assertThat(actualChoices).hasSize(1),
                () -> assertThat(actualChoice.getContent()).isEqualTo(newChoice.getContent()),
                () -> assertThat(actualChoice.getSequence()).isEqualTo(newChoice.getSequence())
        );
    }

    @DisplayName("질문을 수정한다.")
    @Test
    void updateByTsid() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.notStarted());
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
                        newQuestion.isRequired()
                )));

        // when
        questionFacade.update(questionUpdateRequest, applyForm.toStringTsid());

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
                () -> assertThat(actualQuestion.isRequired()).isEqualTo(newQuestion.isRequired()),

                () -> assertThat(actualChoices).hasSize(1),
                () -> assertThat(actualChoice.getContent()).isEqualTo(newChoice.getContent()),
                () -> assertThat(actualChoice.getSequence()).isEqualTo(newChoice.getSequence())
        );
    }

    @DisplayName("모집 공고가 시작된 이후이면 질문을 수정할 수 없다.")
    @Test
    void update_ApplyFormInProgress() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend());
        Question question = questionRepository.save(QuestionFixture.multipleChoiceType(applyForm));
        choiceRepository.save(ChoiceFixture.first(question));
        choiceRepository.save(ChoiceFixture.second(question));

        Question newQuestion = QuestionFixture.shortAnswerType(applyForm);

        QuestionUpdateRequests questionUpdateRequest = new QuestionUpdateRequests(List.of(
                new QuestionCreateRequest(
                        newQuestion.getQuestionType().name(),
                        newQuestion.getContent(),
                        List.of(),
                        newQuestion.getSequence(),
                        newQuestion.isRequired()
                )));

        // when&then
        assertThatThrownBy(() -> questionFacade.update(questionUpdateRequest, applyForm.toStringTsid()))
                .isInstanceOf(QuestionUnmodifiableException.class);
    }
}
