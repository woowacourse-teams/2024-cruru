package com.cruru.email.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ProcessFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailKeywordConverterTest extends ServiceTest {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private EmailKeywordConverter emailKeywordConverter;


    @DisplayName("키워드를 다른 단어로 변환한다.")
    @Test
    void convert() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(defaultDashboard));
        Process process = processRepository.save(ProcessFixture.applyType(defaultDashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        String content = "{AP_NAME}, {CL_NAME}, {RC_TITLE}, {RC_STEP}";


        // when
        String actual = emailKeywordConverter.convert(content, defaultClub, applicant);

        // then
        assertThat(actual).isEqualTo(
                applicant.getName() + ", "
                        + defaultClub.getName() + ", "
                        + applyForm.getTitle() + ", "
                        + process.getName());
    }
}
