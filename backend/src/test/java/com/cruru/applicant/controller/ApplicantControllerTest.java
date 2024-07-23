package com.cruru.applicant.controller;

import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ControllerTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("지원자 컨트롤러 테스트")
class ApplicantControllerTest extends ControllerTest {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @DisplayName("지원자들의 프로세스를 일괄적으로 옮기는 데 성공하면 200을 응답한다.")
    @Test
    void updateApplicantProcess() {
        // given
        Process now = new Process(1L, 0, "서류", "서류 전형", null);
        now = processRepository.save(now);
        Process next = new Process(2L, 1, "최종 합격", "최종 합격", null);
        next = processRepository.save(next);
        Applicant applicant = new Applicant(1L, "name", "email", "phone", now, false);
        applicantRepository.save(applicant);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new ApplicantMoveRequest(List.of(applicant.getId())))
                .when().put("/v1/applicants/move-process/" + next.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자의 기본 정보를 읽어오는 데 성공하면 200을 응답한다.")
    @Test
    void read() {
        // given
        Applicant applicant = applicantRepository.save(new Applicant("name", "email", "phone", null, false));

        // when&then
        RestAssured.given().log().all()
                .when().get("/v1/applicants/" + applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자의 상세 정보를 읽어오는 데 성공하면 200을 응답한다.")
    @Test
    void readDetail() {
        // given
        Dashboard dashboard = dashboardRepository.save(new Dashboard("프론트 부원 모집", null));
        Process process = processRepository.save(new Process(0, "서류", "서류 단계", dashboard));
        Applicant applicant = applicantRepository.save(new Applicant("name", "email", "phone", process, false));

        // when&then
        RestAssured.given().log().all()
                .when().get("/v1/applicants/" + applicant.getId() + "/detail")
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자를 불합격시키는 데 성공하면 200을 응답한다.")
    @Test
    void reject() {
        // given
        Applicant applicant = applicantRepository.save(new Applicant("name", "email", "phone", null, false));

        // when&then
        RestAssured.given().log().all()
                .when().patch("/v1/applicants/" + applicant.getId() + "/reject")
                .then().log().all().statusCode(200);
    }
}
