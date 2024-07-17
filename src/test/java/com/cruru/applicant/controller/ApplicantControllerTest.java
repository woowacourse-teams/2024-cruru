package com.cruru.applicant.controller;

import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@DisplayName("지원자 컨트롤러 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicantControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        applicantRepository.deleteAll();
        processRepository.deleteAll();
    }

    @DisplayName("지원자들의 프로세스를 일괄적으로 옮기는 데 성공하면 200을 응답한다.")
    @Test
    void updateApplicantProcess() {
        Process now = new Process(1L, 0, "서류", "서류 전형", null);
        now = processRepository.save(now);
        Process next = new Process(2L, 1, "최종 합격", "최종 합격", null);
        next = processRepository.save(next);
        Applicant applicant = new Applicant(1L, "name", "email", "phone", now);
        applicantRepository.save(applicant);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ApplicantMoveRequest(List.of(applicant.getId())))
                .when().put("/api/v1/applicants/move-process/" + next.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자의 기본 정보를 읽어오는 데 성공하면 200을 응답한다.")
    @Test
    void read() {
        Applicant applicant = applicantRepository.save(new Applicant("name", "email", "phone", null));

        RestAssured.given().log().all()
                .when().get("/api/v1/applicants/" + applicant.getId())
                .then().log().all().statusCode(200);
    }
}
