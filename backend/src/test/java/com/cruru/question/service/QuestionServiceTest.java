package com.cruru.question.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.choice.controller.dto.ChoiceResponse;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.controller.dto.QuestionResponse;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("질문 서비스 테스트")
class QuestionServiceTest extends ServiceTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private QuestionService questionService;

    @DisplayName("질문 생성에 성공한다.")
    @Test
    void create() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Question question1 = QuestionFixture.shortAnswerType(applyForm);
        QuestionCreateRequest request = new QuestionCreateRequest(
                question1.getQuestionType().toString(),
                question1.getContent(),
                question1.getDescription(),
                List.of(),
                0,
                question1.getRequired()
        );

        // when
        Question question = questionService.create(request, applyForm);

        // then
        List<Question> questions = questionRepository.findAllByApplyForm(applyForm);
        assertThat(questions).hasSize(1);
        assertThat(questions.get(0)).isEqualTo(question);
    }

    @DisplayName("질문 ID를 통해 특정 질문을 조회한다.")
    @Test
    void findById() {
        // given
        Question savedQuestion = questionRepository.save(QuestionFixture.longAnswerType(null));

        // when&then
        assertDoesNotThrow(() -> questionService.findById(savedQuestion.getId()));
        Question actualFoundQuestion = questionService.findById(savedQuestion.getId());
        assertThat(actualFoundQuestion).isEqualTo(savedQuestion);
    }

    @DisplayName("Question 엔티티의 정보를 이용하여 Response DTO로 변경한다.")
    @ParameterizedTest()
    @MethodSource("provideQuestionsAndResponses")
    void toQuestionResponse(Question expectedQuestion, QuestionResponse actualResponse) {
        // given&when&then
        assertAll(
                () -> assertThat(actualResponse.id()).isEqualTo(expectedQuestion.getId()),
                () -> assertThat(actualResponse.orderIndex()).isEqualTo(expectedQuestion.getSequence()),
                () -> assertThat(actualResponse.type()).isEqualTo(expectedQuestion.getQuestionType().toString()),
                () -> assertThat(actualResponse.content()).isEqualTo(expectedQuestion.getContent()),
                () -> assertThat(actualResponse.description()).isEqualTo(expectedQuestion.getDescription())
        );
    }

    private Stream<Arguments> provideQuestionsAndResponses() {
        List<Question> savedQuestions = questionRepository.saveAll(QuestionFixture.allTypes(null))
                .stream()
                .sorted(Comparator.comparing(Question::getSequence))
                .toList();

        List<QuestionResponse> questionResponses = questionService.toQuestionResponses(savedQuestions)
                .stream()
                .sorted(Comparator.comparing(QuestionResponse::orderIndex))
                .toList();

        return IntStream.range(0, savedQuestions.size())
                .mapToObj(i -> Arguments.of(savedQuestions.get(i), questionResponses.get(i)));
    }

    @DisplayName("선택지를 가지고 있지 않는 질문은 ChoiceResponse 목록이 비어있어야 한다.")
    @Test
    void toQuestionResponse_NotHavingChoicesQuestion() {
        // given
        List<Question> nonChoiceTypeQuestions = questionRepository.saveAll(QuestionFixture.nonChoiceType(null));

        // when
        List<QuestionResponse> questionResponses = questionService.toQuestionResponses(nonChoiceTypeQuestions);

        // then
        List<ChoiceResponse> choiceResponses1 = questionResponses.get(0).choiceResponses();
        List<ChoiceResponse> choiceResponses2 = questionResponses.get(1).choiceResponses();
        assertAll(
                () -> assertThat(choiceResponses1).isEmpty(),
                () -> assertThat(choiceResponses2).isEmpty()
        );
    }
}
