package com.cruru.choice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.choice.controller.dto.ChoiceCreateRequest;
import com.cruru.choice.domain.Choice;
import com.cruru.choice.domain.repository.ChoiceRepository;
import com.cruru.choice.exception.badrequest.ChoiceEmptyException;
import com.cruru.choice.exception.badrequest.ChoiceIllegalSaveException;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ChoiceFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("선택지 서비스 테스트")
class ChoiceServiceTest extends ServiceTest {

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @DisplayName("특정 객관식 Question에 속하는 다수의 선택지를 저장한다.")
    @Test
    void createAll() {
        // given
        Question question = questionRepository.save(QuestionFixture.dropdownType(null));
        List<Choice> choices = ChoiceFixture.fiveChoices(question);
        List<ChoiceCreateRequest> choiceRequests = choices.stream()
                .map(choice -> new ChoiceCreateRequest(choice.getContent(), choice.getSequence()))
                .toList();

        // when
        List<Choice> actualChoices = choiceService.createAll(choiceRequests, question);

        // then

        assertThat(actualChoices).hasSize(choices.size());
    }

    @DisplayName("주관식 Question의 선택지를 저장을 시도하면 예외를 던진다.")
    @Test
    void createAllThrowsWithIllegalSaveException() {
        // given
        Question shortAnswerQuestion = questionRepository.save(QuestionFixture.shortAnswerType(null));
        Question longAnswerQuestion = questionRepository.save(QuestionFixture.longAnswerType(null));
        List<Choice> choices = ChoiceFixture.fiveChoices(shortAnswerQuestion);
        List<ChoiceCreateRequest> choiceRequests = choices.stream()
                .map(choice -> new ChoiceCreateRequest(choice.getContent(), choice.getSequence()))
                .toList();

        // when & then
        assertAll(
                () -> assertThatThrownBy(() -> choiceService.createAll(choiceRequests, shortAnswerQuestion))
                        .isInstanceOf(ChoiceIllegalSaveException.class),

                () -> assertThatThrownBy(() -> choiceService.createAll(choiceRequests, longAnswerQuestion))
                        .isInstanceOf(ChoiceIllegalSaveException.class)
        );
    }

    @DisplayName("객관식 Question에 선택지가 존재하지 않으면 예외를 던진다.")
    @Test
    void createAllThrowsWithChoiceEmptyBadRequestException() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Question dropdownQuestion = questionRepository.save(QuestionFixture.dropdownType(applyForm));
        List<ChoiceCreateRequest> choiceRequests = List.of();

        // when&then
        assertThatThrownBy(() -> choiceService.createAll(choiceRequests, dropdownQuestion))
                .isInstanceOf(ChoiceEmptyException.class);
    }

    @DisplayName("객관식 질문의 모든 선택지를 조회한다.")
    @Test
    void findAllByQuestion() {
        // given
        Question question = questionRepository.save(QuestionFixture.dropdownType(null));
        List<Choice> choices = choiceRepository.saveAll(ChoiceFixture.fiveChoices(question));

        // when
        List<Choice> actualChoices = choiceService.findAllByQuestion(question);

        // then
        int expectedSize = choices.size();
        assertThat(actualChoices).hasSize(expectedSize);
    }
}
