package com.cruru.question.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Answer;
import com.cruru.question.domain.Question;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.AnswerFixture;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("주관식 답변 레포지토리 테스트")
class AnswerRepositoryTest extends RepositoryTest {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        answerRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 답변을 저장하면, 해당 ID의 답변은 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Question question = QuestionFixture.shortAnswerType(null);
        Answer answer = new Answer("체육 전공입니다.", question, null);
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
        Question question = QuestionFixture.shortAnswerType(null);
        Answer answer1 = new Answer("체육 전공입니다.", question, null);
        Answer answer2 = new Answer("음악 전공입니다.", question, null);

        //when
        Answer savedAnswer1 = answerRepository.save(answer1);
        Answer savedAnswer2 = answerRepository.save(answer2);

        //then
        assertThat(savedAnswer1.getId() + 1).isEqualTo(savedAnswer2.getId());
    }

    @DisplayName("특정 지원자와 질문에 해당하는 답변 목록을 조회한다.")
    @Test
    void findAllByApplicantWithQuestions() {
        // given
        Process process = processRepository.save(ProcessFixture.applyType());
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        Question question = questionRepository.save(QuestionFixture.shortAnswerType(null));
        answerRepository.save(AnswerFixture.first(question, applicant));

        // when
        List<Answer> actual = answerRepository.findAllByApplicantWithQuestions(applicant);

        // then
        Answer actualAnswer = actual.get(0);
        assertAll(
                () -> assertThat(actual).hasSize(1),
                () -> assertThat(actualAnswer.getQuestion()).isEqualTo(question),
                () -> assertThat(actualAnswer.getApplicant()).isEqualTo(applicant)
        );
    }

    @DisplayName("지원자 목록에 따라 모두 삭제한다.")
    @Test
    void deleteAllByApplicants() {
        // given
        Question question = questionRepository.save(QuestionFixture.shortAnswerType(null));

        Applicant applicant1 = applicantRepository.save(ApplicantFixture.pendingDobby());
        Applicant applicant2 = applicantRepository.save(ApplicantFixture.pendingDobby());
        Applicant applicant3 = applicantRepository.save(ApplicantFixture.pendingDobby());
        List<Applicant> applicants = List.of(applicant1, applicant2);

        Answer answer1 = answerRepository.save(AnswerFixture.first(question, applicant1));
        Answer answer2 = answerRepository.save(AnswerFixture.second(question, applicant2));
        Answer answer3 = answerRepository.save(AnswerFixture.second(question, applicant3));

        // when
        answerRepository.deleteAllByApplicants(applicants);

        // then
        assertThat(answerRepository.findAll()).contains(answer3)
                .doesNotContain(answer1, answer2);
    }
}
