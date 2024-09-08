package com.cruru.applicant.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

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
        Process now = processRepository.save(ProcessFixture.applyType());
        Process next = processRepository.save(ProcessFixture.approveType());
        Applicant applicant = ApplicantFixture.pendingDobby(now);
        applicantRepository.save(applicant);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(new ApplicantMoveRequest(List.of(applicant.getId())))
                .filter(document("applicant/move-process/",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("process_id").description("지원자들이 옮겨질 프로세스의 id")),
                        requestFields(fieldWithPath("applicantIds").description("프로세스를 옮길 지원자들의 id"))
                ))
                .when().put("/v1/applicants/move-process/{process_id}", next.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 프로세스로 지원자를 옮기려 시도하면 404를 응답한다.")
    @Test
    void updateApplicantProcess_processNotFound() {
        // given
        Process now = processRepository.save(ProcessFixture.applyType());
        Applicant applicant = ApplicantFixture.pendingDobby(now);
        applicantRepository.save(applicant);
        long invalidProcessId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(new ApplicantMoveRequest(List.of(applicant.getId())))
                .filter(document("applicant/move-process-fail/process-not-found/",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("process_id").description("존재하지 않는 프로세스의 id")),
                        requestFields(fieldWithPath("applicantIds").description("프로세스를 옮길 지원자들의 id"))
                ))
                .when().put("/v1/applicants/move-process/{process_id}", invalidProcessId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원자의 기본 정보를 읽어오는 데 성공하면 200을 응답한다.")
    @Test
    void read() {
        // given
        Process process = processRepository.save(ProcessFixture.applyType());
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .filter(document("applicant/read-profile",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicant_id").description("지원자의 id"))
                ))
                .when().get("/v1/applicants/{applicant_id}", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 정보 조회를 시도하면 404를 응답한다.")
    @Test
    void read_applicantNotFound() {
        // given
        long invalidApplicantId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .filter(document("applicant/read-profile-fail/applicant-not-found/",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicant_id").description("존재하지 않는 지원자의 id"))
                ))
                .when().get("/v1/applicants/{applicant_id}", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원자의 상세 정보를 읽어오는 데 성공하면 200을 응답한다.")
    @Test
    void readDetail() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        Process process = processRepository.save(ProcessFixture.applyType(dashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .filter(document("applicant/read-detail-profile",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicant_id").description("지원자의 id"))
                ))
                .when().get("/v1/applicants/{applicant_id}/detail", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원자를 불합격시키는 데 성공하면 200을 응답한다.")
    @Test
    void reject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .filter(document("applicant/reject",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicant_id").description("지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicant_id}/reject", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 불합격 시, 404를 응답한다.")
    @Test
    void reject_applicantNotFound() {
        // given
        long invalidApplicantId = 1;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .filter(document("applicant/reject-fail/applicant-not-found/",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicant_id").description("지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicant_id}/reject", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("이미 불합격한 지원자일 경우 400를 응답한다.")
    @Test
    void reject_alreadyReject() {
        // given
        Applicant applicant = ApplicantFixture.pendingDobby();
        applicant.reject();
        Applicant savedApplicant = applicantRepository.save(applicant);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .filter(document("applicant/reject-fail/already-rejected/",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicant_id").description("존재하지 않는 지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicant_id}/reject", savedApplicant.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원자를 불합격 해제시키는 데 성공하면 200을 응답한다.")
    @Test
    void unreject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.rejectedRush());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .filter(document("applicant/unreject",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicant_id").description("지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicant_id}/unreject", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 불합격 해제 시, 404를 응답한다.")
    @Test
    void unreject_applicantNotFound() {
        // given
        long invalidApplicantId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .filter(document("applicant/unreject-fail/applicant-not-found/",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicant_id").description("존재하지 않는 지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicant_id}/unreject", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원자 정보 변경에 성공하면 200을 응답한다.")
    @Test
    void updateInformation() {
        // given
        String toChangeName = "도비";
        String toChangeEmail = "dev.DOBBY@gmail.com";
        String toChangePhone = "01011111111";
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());
        ApplicantUpdateRequest request = new ApplicantUpdateRequest(toChangeName, toChangeEmail, toChangePhone);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applicant/change-info",
                        requestCookies(cookieWithName("token").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicant_id").description("지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicant_id}", applicant.getId())
                .then().log().all().statusCode(200);
    }
}
