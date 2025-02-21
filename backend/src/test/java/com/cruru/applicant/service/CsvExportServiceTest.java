package com.cruru.applicant.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Answer;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.AnswerRepository;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.AnswerFixture;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
import groovy.util.logging.Slf4j;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DisplayName("CSV 파일 쓰기 서비스 테스트")
class CsvExportServiceTest extends ServiceTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Autowired
    private CsvExportService csvExportService;
    @Autowired
    private ApplyFormRepository applyFormRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("빈 데이터일 경우 CSV 헤더만 생성되는지 테스트")
    void exportApplicantsToCsv_emptyData() {
        long nonExistentApplyFormId = 999L;

        ByteArrayInputStream csvStream = csvExportService.exportApplicantsToCsv(nonExistentApplyFormId);
        String csvText = toStringWithoutBom(csvStream);

        assertThat(csvText).contains("이름,이메일,전화번호,제출일시");
    }

    @Test
    @DisplayName("질문 2개, 지원자 1명, 답변 2개 정상 생성 시 CSV 내용 확인")
    void exportApplicantsToCsv_singleApplicant() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(defaultDashboard));
        Process process = processRepository.save(ProcessFixture.applyType(defaultDashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingRush(process));

        Question question1 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));
        Question question2 = questionRepository.save(QuestionFixture.longAnswerType(applyForm));

        Answer answer1 = answerRepository.save(AnswerFixture.first(question1, applicant));
        Answer answer2 = answerRepository.save(AnswerFixture.second(question2, applicant));

        // when
        ByteArrayInputStream csvStream = csvExportService.exportApplicantsToCsv(applyForm.getId());
        String csvText = toStringWithoutBom(csvStream);

        // then
        String expectedHeader = String.join(",",
                "이름", "이메일", "전화번호", "제출일시",
                question1.getContent(), question2.getContent()
        );

        String expectedDataRow = String.join(",",
                applicant.getName(),
                applicant.getEmail(),
                formatPhoneNumber(applicant.getPhone()),
                applicant.getCreatedDate().format(DATE_TIME_FORMATTER),
                answer1.getContent(),
                answer2.getContent()
        );

        Assertions.assertAll(
                () -> assertThat(csvText.trim()).contains(expectedHeader.trim()),
                () -> assertThat(csvText.trim()).contains(expectedDataRow.trim())
        );
    }

    private String toStringWithoutBom(ByteArrayInputStream stream) {
        String text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        return text.replace("\uFEFF", "").replace("\r\n", "\n"); // BOM과 CRLF 제거
    }

    private static String formatPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return "";
        }

        String onlyDigits = phone.replaceAll("[^0-9]", "");

        if (onlyDigits.length() == 11 && onlyDigits.startsWith("01")) {
            return onlyDigits.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        }

        if (onlyDigits.length() == 10) {
            return onlyDigits.replaceFirst("(\\d{2,3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
        }

        if (Pattern.matches(".*-.*", phone)) {
            return phone;
        }

        return phone;
    }
}
