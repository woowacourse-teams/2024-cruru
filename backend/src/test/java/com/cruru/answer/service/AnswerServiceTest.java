package com.cruru.answer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.answer.dto.AnswerResponse;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.AnswerFixture;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.util.List;
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

    @DisplayName("지원자의 응답을 조회한다.")
    @Test
    void findAllByApplicant() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.createPendingApplicantDobby());
        Question question1 = questionRepository.save(QuestionFixture.createShortAnswerQuestion(null));
        Question question2 = questionRepository.save(QuestionFixture.createShortAnswerQuestion(null));

        List<Answer> expectedAnswers = answerRepository.saveAll(List.of(
                AnswerFixture.fristAnswer(question1, applicant),
                AnswerFixture.secondAnswer(question2, applicant)
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
        Applicant applicant = applicantRepository.save(ApplicantFixture.createPendingApplicantDobby());
        Question question1 = questionRepository.save(QuestionFixture.createShortAnswerQuestion(null));
        Question question2 = questionRepository.save(QuestionFixture.createShortAnswerQuestion(null));

        Answer expectedAnswer1 = AnswerFixture.fristAnswer(question1, applicant);
        Answer expectedAnswer2 = AnswerFixture.secondAnswer(question2, applicant);
        List<Answer> expectedAnswers = List.of(expectedAnswer1, expectedAnswer2);

        // when
        List<AnswerResponse> actualAnswerResponses = answerService.toAnswerResponses(expectedAnswers);

        // then
        AnswerResponse actualAnswerResponse1 = actualAnswerResponses.get(0);
        AnswerResponse actualAnswerResponse2 = actualAnswerResponses.get(1);
        assertAll(() -> {
            assertThat(actualAnswerResponses).hasSize(2);

            assertThat(actualAnswerResponse1.answer()).isEqualTo(expectedAnswer1.getContent());
            assertThat(actualAnswerResponse2.answer()).isEqualTo(expectedAnswer2.getContent());

            assertThat(actualAnswerResponse1.question()).isEqualTo(question1.getContent());
            assertThat(actualAnswerResponse2.question()).isEqualTo(question2.getContent());
        });
    }
}
