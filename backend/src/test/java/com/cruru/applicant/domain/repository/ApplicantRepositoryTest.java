package com.cruru.applicant.domain.repository;

import static com.cruru.util.fixture.ApplicantFixture.createApplicantDobby;
import static com.cruru.util.fixture.ApplicantFixture.createApplicantRush;
import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.Applicant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("지원자 레포지토리 테스트")
@DataJpaTest
class ApplicantRepositoryTest {

    @Autowired
    private ApplicantRepository applicantRepository;

    @BeforeEach
    void setUp() {
        applicantRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 프로세스를 저장하면, 해당 ID의 프로세스는 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Applicant applicant = createApplicantDobby();
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
        Applicant applicant1 = createApplicantDobby();
        Applicant applicant2 = createApplicantRush();

        //when
        Applicant savedApplicant1 = applicantRepository.save(applicant1);
        Applicant savedApplicant2 = applicantRepository.save(applicant2);

        //then
        assertThat(savedApplicant1.getId() + 1).isEqualTo(savedApplicant2.getId());
    }
}
