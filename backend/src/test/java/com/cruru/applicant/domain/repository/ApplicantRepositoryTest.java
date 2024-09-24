package com.cruru.applicant.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.RepositoryTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.ProcessFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지원자 레포지토리 테스트")
class ApplicantRepositoryTest extends RepositoryTest {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @BeforeEach
    void setUp() {
        applicantRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 프로세스를 저장하면, 해당 ID의 프로세스는 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Applicant applicant = ApplicantFixture.pendingDobby();
        Applicant saved = applicantRepository.save(applicant);

        //when
        Applicant updatedApplicant = new Applicant(saved.getId(), "다른이름", "다른이메일", "다른번호", null, false);
        applicantRepository.save(updatedApplicant);

        //then
        Applicant foundApplicant = applicantRepository.findById(saved.getId()).get();
        assertThat(foundApplicant.getName()).isEqualTo("다른이름");
        assertThat(foundApplicant.getEmail()).isEqualTo("다른이메일");
        assertThat(foundApplicant.getPhone()).isEqualTo("다른번호");
    }

    @DisplayName("ID가 없는 프로세스를 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        Applicant applicant1 = ApplicantFixture.pendingDobby();
        Applicant applicant2 = ApplicantFixture.pendingRush();

        //when
        Applicant savedApplicant1 = applicantRepository.save(applicant1);
        Applicant savedApplicant2 = applicantRepository.save(applicant2);

        //then
        assertThat(savedApplicant1.getId() + 1).isEqualTo(savedApplicant2.getId());
    }

    @DisplayName("특정 Process에 대한 ApplicantCard 목록을 반환한다.")
    @Test
    void findApplicantCardsByProcess() {
        // given
        Process process = processRepository.save(ProcessFixture.applyType());

        Applicant applicant1 = ApplicantFixture.pendingDobby(process);
        Applicant applicant2 = ApplicantFixture.pendingRush(process);
        applicantRepository.saveAll(List.of(applicant1, applicant2));

        List<Evaluation> evaluations = List.of(
                EvaluationFixture.fivePoints(process, applicant1),
                EvaluationFixture.fivePoints(process, applicant1),
                EvaluationFixture.fivePoints(process, applicant2)
        );
        evaluationRepository.saveAll(evaluations);

        // when
        List<ApplicantCard> applicantCards = applicantRepository.findApplicantCardsByProcesses(List.of(process));

        // then
        assertThat(applicantCards).hasSize(2);

        ApplicantCard applicantCard1 = applicantCards.get(0);
        assertAll(
                () -> assertThat(applicantCard1.id()).isEqualTo(applicant1.getId()),
                () -> assertThat(applicantCard1.name()).isEqualTo(applicant1.getName()),
                () -> assertThat(applicantCard1.evaluationCount()).isEqualTo(2),
                () -> assertThat(applicantCard1.averageScore()).isEqualTo(5.0)
        );

        ApplicantCard applicantCard2 = applicantCards.get(1);
        assertAll(
                () -> assertThat(applicantCard2.id()).isEqualTo(applicant2.getId()),
                () -> assertThat(applicantCard2.name()).isEqualTo(applicant2.getName()),
                () -> assertThat(applicantCard2.evaluationCount()).isEqualTo(1),
                () -> assertThat(applicantCard2.averageScore()).isEqualTo(5.0)
        );
    }

    @DisplayName("평가가 없을 경우 ApplicantCard 목록에서 평균 점수는 0점이고 카운트는 0이다.")
    @Test
    void findApplicantCardsByProcess_noEvaluations() {
        // given
        Process process = processRepository.save(ProcessFixture.applyType());
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));

        // when
        List<ApplicantCard> applicantCards = applicantRepository.findApplicantCardsByProcesses(List.of(process));

        // then
        assertThat(applicantCards).hasSize(1);
        ApplicantCard applicantCard = applicantCards.get(0);

        assertAll(
                () -> assertThat(applicantCard.id()).isEqualTo(applicant.getId()),
                () -> assertThat(applicantCard.name()).isEqualTo(applicant.getName()),
                () -> assertThat(applicantCard.evaluationCount()).isEqualTo(0),
                () -> assertThat(applicantCard.averageScore()).isEqualTo(0.0)
        );
    }
}
