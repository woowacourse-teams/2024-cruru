package com.cruru.question.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.question.domain.Answer;
import com.cruru.question.domain.repository.AnswerRepository;
import com.cruru.question.controller.response.AnswerResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.controller.dto.AnswerCreateRequest;
import com.cruru.applyform.exception.badrequest.ReplyNotExistsException;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.AnswerFixture;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("Answer 서비스 테스트")
class AnswerServiceTest extends ServiceTest {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Applicant applicant;
    private Question question1;
    private Question question2;

    @BeforeEach
    void setUp() {
        applicant = applicantRepository.save(ApplicantFixture.pendingDobby());
        question1 = questionRepository.save(QuestionFixture.shortAnswerType(null));
        question2 = questionRepository.save(QuestionFixture.dropdownType(null));
    }

    @DisplayName("질문에 대한 지원자의 답변을 성공적으로 저장한다.")
    @Test
    void savedAnswerReplies() {
        // given
        String reply = "첫 번째 단답";
        List<String> replies1 = List.of(reply);
        AnswerCreateRequest request = new AnswerCreateRequest(question1.getId(), replies1);

        // when
        answerService.saveAnswerReplies(request, question1, applicant);

        // then
        List<Answer> actualAnswer = answerRepository.findAllByApplicant(applicant);
        String content = actualAnswer.get(0).getContent();
        assertAll(
                () -> assertThat(actualAnswer).hasSize(1),
                () -> assertThat(content).isEqualTo(reply)
        );
    }

    @DisplayName("선택 질문에 대한 지원자의 답변이 존재하지 않으면 빈 문자열을 저장한다.")
    @Test
    void savedAnswerReplies_notRequiredReplyNotExists() {
        // given
        List<String> replies = List.of();
        Question question = questionRepository.save(QuestionFixture.shortAnswerType(null));
        AnswerCreateRequest request = new AnswerCreateRequest(question.getId(), replies);

        // then
        answerService.saveAnswerReplies(request, question, applicant);

        // then
        List<Answer> actualAnswer = answerRepository.findAllByApplicant(applicant);
        String content = actualAnswer.get(0).getContent();
        assertAll(
                () -> assertThat(actualAnswer).hasSize(1),
                () -> assertThat(content).isEqualTo("")
        );
    }

    @DisplayName("필수 질문에 대한 지원자의 답변이 존재하지 않으면 예외가 발생한다.")
    @Test
    void savedAnswerReplies_requiredReplyNotExists() {
        // given
        List<String> replies = List.of();
        Question question = questionRepository.save(QuestionFixture.required(null));
        AnswerCreateRequest request = new AnswerCreateRequest(question.getId(), replies);

        // when&then
        assertThatThrownBy(() -> answerService.saveAnswerReplies(request, question, applicant))
                .isInstanceOf(ReplyNotExistsException.class);
    }

    @DisplayName("지원자의 응답을 조회한다.")
    @Test
    void findAllByApplicant() {
        // given
        List<Answer> expectedAnswers = answerRepository.saveAll(List.of(
                AnswerFixture.first(question1, applicant),
                AnswerFixture.second(question2, applicant)
        ));

        // when
        List<Answer> actualAnswers = answerService.findAllByApplicant(applicant);

        // then
        assertThat(actualAnswers).hasSameElementsAs(expectedAnswers);
    }

    @DisplayName("도메인 엔티티를 DTO로 변환한다.")
    @Test
    void toAnswerResponses() {
        // given
        Answer expectedAnswer1 = AnswerFixture.first(question1, applicant);
        Answer expectedAnswer2 = AnswerFixture.second(question2, applicant);
        List<Answer> expectedAnswers = List.of(expectedAnswer1, expectedAnswer2);

        // when
        List<AnswerResponse> actualAnswerResponses = answerService.toAnswerResponses(expectedAnswers);

        // then
        AnswerResponse actualAnswerResponse1 = actualAnswerResponses.get(0);
        AnswerResponse actualAnswerResponse2 = actualAnswerResponses.get(1);
        assertAll(
                () -> assertThat(actualAnswerResponses).hasSize(2),

                () -> assertThat(actualAnswerResponse1.answer()).isEqualTo(expectedAnswer1.getContent()),
                () -> assertThat(actualAnswerResponse2.answer()).isEqualTo(expectedAnswer2.getContent()),

                () -> assertThat(actualAnswerResponse1.question()).isEqualTo(question1.getContent()),
                () -> assertThat(actualAnswerResponse2.question()).isEqualTo(question2.getContent())
        );
    }

    @DisplayName("다중 선택 답변은 하나의 답변으로 묶어 DTO로 변환한다.")
    @Test
    void toAnswerResponses_multipleChoice() {
        // given
        Question question = questionRepository.save(QuestionFixture.multipleChoiceType(null));

        Answer expectedAnswer1 = AnswerFixture.first(question, applicant);
        Answer expectedAnswer2 = AnswerFixture.second(question, applicant);
        Answer expectedAnswer3 = AnswerFixture.second(question, applicant);

        List<Answer> expectedAnswers = List.of(expectedAnswer1, expectedAnswer2, expectedAnswer3);

        // when
        List<AnswerResponse> actualAnswerResponses = answerService.toAnswerResponses(expectedAnswers);

        // then
        assertAll(
                () -> assertThat(actualAnswerResponses).hasSize(1),
                () -> assertThat(actualAnswerResponses.get(0).answer()).contains(expectedAnswer1.getContent()),
                () -> assertThat(actualAnswerResponses.get(0).answer()).contains(expectedAnswer2.getContent()),
                () -> assertThat(actualAnswerResponses.get(0).answer()).contains(expectedAnswer3.getContent())
        );
    }
}
