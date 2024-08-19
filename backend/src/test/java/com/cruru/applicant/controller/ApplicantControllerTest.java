package com.cruru.applicant.controller;

import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.controller.dto.ApplicantUpdateRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.ProcessFixture;
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
    void updateProcess() {
        // given
        Process now = processRepository.save(ProcessFixture.first());
        Process next = processRepository.save(ProcessFixture.last());
        Applicant applicant = ApplicantFixture.pendingDobby(now);
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
        Process process = processRepository.save(ProcessFixture.first());
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));

        // when&then
        RestAssured.given().log().all()
                .when().get("/v1/applicants/" + applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자의 상세 정보를 읽어오는 데 성공하면 200을 응답한다.")
    @Test
    void readDetail() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        Process process = processRepository.save(ProcessFixture.first(dashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));

        // when&then
        RestAssured.given().log().all()
                .when().get("/v1/applicants/" + applicant.getId() + "/detail")
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자를 불합격시키는 데 성공하면 200을 응답한다.")
    @Test
    void reject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());

        // when&then
        RestAssured.given().log().all()
                .when().patch("/v1/applicants/" + applicant.getId() + "/reject")
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자를 불합격 해제시키는 데 성공하면 200을 응답한다.")
    @Test
    void unreject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.rejectedRush());

        // when&then
        RestAssured.given().log().all()
                .when().patch("/v1/applicants/{applicantId}/unreject", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자 정보 변경에 성공하면 200을 응답한다.")
    @Test
    void updateInformation() {
        // given
        String toChangeName = "도비";
        String toChangeEmail = "dev.DOBBY@gmail.com";
        String toChangePhone = "010111111111";
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());
        ApplicantUpdateRequest request = new ApplicantUpdateRequest(toChangeName, toChangeEmail, toChangePhone);

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().patch("/v1/applicants/" + applicant.getId())
                .then().log().all().statusCode(200);
    }
}
