package com.cruru.applicant.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.applicant.controller.request.ApplicantMoveRequest;
import com.cruru.applicant.controller.request.ApplicantUpdateRequest;
import com.cruru.applicant.controller.request.ApplicantsRejectRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.AnswerRepository;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.AnswerFixture;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.FieldDescriptor;

@DisplayName("지원자 컨트롤러 테스트")
class ApplicantControllerTest extends ControllerTest {

    private static final FieldDescriptor[] ANSWER_RESPONSE_FIELD_DESCRIPTORS = {
            fieldWithPath("orderIndex").description("순서"),
            fieldWithPath("question").description("질문"),
            fieldWithPath("answer").description("질문에 대한 응답")
    };

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @DisplayName("지원자들의 프로세스를 일괄적으로 옮기는 데 성공하면 200을 응답한다.")
    @Test
    void updateProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        Process now = processRepository.save(ProcessFixture.applyType(dashboard));
        Process next = processRepository.save(ProcessFixture.approveType(dashboard));
        Applicant applicant = ApplicantFixture.pendingDobby(now);
        applicantRepository.save(applicant);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(new ApplicantMoveRequest(List.of(applicant.getId())))
                .filter(document(
                        "applicant/move-process/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("processId").description("지원자들이 옮겨질 프로세스의 id")),
                        requestFields(fieldWithPath("applicantIds").description("프로세스를 옮길 지원자들의 id"))
                ))
                .when().put("/v1/applicants/move-process/{processId}", next.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 프로세스로 지원자를 옮기려 시도하면 404를 응답한다.")
    @Test
    void updateApplicantProcess_processNotFound() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        Process now = processRepository.save(ProcessFixture.applyType(dashboard));
        Applicant applicant = ApplicantFixture.pendingDobby(now);
        applicantRepository.save(applicant);
        Long invalidProcessId = -1L;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(new ApplicantMoveRequest(List.of(applicant.getId())))
                .filter(document(
                        "applicant/move-process-fail/process-not-found/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("processId").description("존재하지 않는 프로세스의 id")),
                        requestFields(fieldWithPath("applicantIds").description("프로세스를 옮길 지원자들의 id"))
                ))
                .when().put("/v1/applicants/move-process/{processId}", invalidProcessId)
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
                .cookie("accessToken", token)
                .filter(document(
                        "applicant/read-profile",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("지원자의 id")),
                        responseFields(
                                fieldWithPath("applicant.id").description("지원자의 id"),
                                fieldWithPath("applicant.name").description("지원자의 이름"),
                                fieldWithPath("applicant.email").description("지원자의 이메일"),
                                fieldWithPath("applicant.phone").description("지원자의 전화번호"),
                                fieldWithPath("applicant.isRejected").description("지원자의 불합격 여부"),
                                fieldWithPath("applicant.createdAt").description("지원자의 생성날짜"),
                                fieldWithPath("process.id").description("프로세스의 id"),
                                fieldWithPath("process.name").description("프로세스 이름")
                        )
                ))
                .when().get("/v1/applicants/{applicantId}", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 정보 조회를 시도하면 404를 응답한다.")
    @Test
    void read_applicantNotFound() {
        // given
        Long invalidApplicantId = -1L;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document(
                        "applicant/read-profile-fail/applicant-not-found/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("존재하지 않는 지원자의 id"))
                ))
                .when().get("/v1/applicants/{applicantId}", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원자의 상세 정보를 읽어오는 데 성공하면 200을 응답한다.")
    @Test
    void readDetail() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        Process process = processRepository.save(ProcessFixture.applyType(dashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Question question = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));
        answerRepository.save(AnswerFixture.first(question, applicant));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "applicant/read-detail-profile",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("지원자의 id")),
                        responseFields(
                                fieldWithPath("details").description("답변들")
                        ).andWithPrefix("details[].", ANSWER_RESPONSE_FIELD_DESCRIPTORS)
                ))
                .when().get("/v1/applicants/{applicantId}/detail", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 정보 조회를 시도하면 404를 응답한다.")
    @Test
    void readDetail_applicantNotFound() {
        // given
        long invalidApplicantId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "applicant/read-detail-profile-fail/applicant-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("지원자의 id"))
                ))
                .when().get("/v1/applicants/{applicantId}/detail", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원자를 불합격시키는 데 성공하면 200을 응답한다.")
    @Test
    void reject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "applicant/reject",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicantId}/reject", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 불합격 시, 404를 응답한다.")
    @Test
    void reject_applicantNotFound() {
        // given
        long invalidApplicantId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document(
                        "applicant/reject-fail/applicant-not-found/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicantId}/reject", invalidApplicantId)
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
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document(
                        "applicant/reject-fail/already-rejected/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("존재하지 않는 지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicantId}/reject", savedApplicant.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원자를 불합격 해제시키는 데 성공하면 200을 응답한다.")
    @Test
    void unreject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.rejectedRush());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "applicant/unreject",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicantId}/unreject", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 불합격 해제 시, 404를 응답한다.")
    @Test
    void unreject_applicantNotFound() {
        // given
        long invalidApplicantId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document(
                        "applicant/unreject-fail/applicant-not-found/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("존재하지 않는 지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicantId}/unreject", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("불합격하지 않은 지원자 불합격 해제 시, 400를 응답한다.")
    @Test
    void unreject_notRejected() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document(
                        "applicant/unreject-fail/applicant-not-rejected/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("불합격하지 않는 지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicantId}/unreject", applicant.getId())
                .then().log().all().statusCode(400);
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
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "applicant/change-info",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicantId}", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자의 정보 변경 시, 404를 응답한다.")
    @Test
    void updateInformation_applicantNotFound() {
        // given
        long invalidApplicantId = -1;
        String toChangeName = "도비";
        String toChangeEmail = "dev.DOBBY@gmail.com";
        String toChangePhone = "01011111111";
        ApplicantUpdateRequest request = new ApplicantUpdateRequest(toChangeName, toChangeEmail, toChangePhone);

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "applicant/change-info-fail/applicant-not-found/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applicantId").description("존재하지 않는 지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicantId}", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("모든 지원자를 불합격시키는 데 성공하면 200을 응답한다.")
    @Test
    void rejectAll() {
        // given
        Applicant applicant1 = applicantRepository.save(ApplicantFixture.pendingDobby());
        Applicant applicant2 = applicantRepository.save(ApplicantFixture.pendingRush());
        ApplicantsRejectRequest request = new ApplicantsRejectRequest(List.of(applicant1.getId(), applicant2.getId()));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "applicant/reject-all/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        requestFields(fieldWithPath("applicantIds").description("불합격 시킬 지원자들의 id"))
                ))
                .when().patch("/v1/applicants/reject")
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 불합격 시, 404를 응답한다.")
    @Test
    void rejectAll_applicantNotFound() {
        // given
        ApplicantsRejectRequest request = new ApplicantsRejectRequest(List.of(-1L));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "applicant/reject-all-fail/applicant-not-found/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        requestFields(fieldWithPath("applicantIds").description("존재하지 않는 지원자의 id"))
                ))
                .when().patch("/v1/applicants/reject")
                .then().log().all().statusCode(404);
    }

    @DisplayName("불합격 한 지원자 불합격 시, 400를 응답한다.")
    @Test
    void rejectAll_alreadyReject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.rejectedRush());
        ApplicantsRejectRequest request = new ApplicantsRejectRequest(List.of(applicant.getId()));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "applicant/reject-all-fail/already-rejected/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        requestFields(fieldWithPath("applicantIds").description("이미 불합격한 지원자의 id"))
                ))
                .when().patch("/v1/applicants/reject")
                .then().log().all().statusCode(400);
    }

    @DisplayName("모든 지원자를 불합격 해제시키는데 성공하면 200을 응답한다.")
    @Test
    void unrejectAll() {
        // given
        Applicant applicant1 = applicantRepository.save(ApplicantFixture.rejectedRush());
        Applicant applicant2 = applicantRepository.save(ApplicantFixture.rejectedRush());
        ApplicantsRejectRequest request = new ApplicantsRejectRequest(List.of(applicant1.getId(), applicant2.getId()));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "applicant/unreject-all/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        requestFields(fieldWithPath("applicantIds").description("불합격 해제시킬 지원자들의 id"))
                ))
                .when().patch("/v1/applicants/unreject")
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자 불합격 해제 시, 404를 응답한다.")
    @Test
    void unrejectAll_applicantNotFound() {
        // given
        ApplicantsRejectRequest request = new ApplicantsRejectRequest(List.of(-1L));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "applicant/unreject-all-fail/applicant-not-found/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        requestFields(fieldWithPath("applicantIds").description("존재하지 않는 지원자의 id"))
                ))
                .when().patch("/v1/applicants/unreject")
                .then().log().all().statusCode(404);
    }

    @DisplayName("불합격 한 지원자 불합격 해제 시, 400를 응답한다.")
    @Test
    void unrejectAll_alreadyUnreject() {
        // given
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby());
        ApplicantsRejectRequest request = new ApplicantsRejectRequest(List.of(applicant.getId()));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document(
                        "applicant/unreject-all-fail/already-unrejected/",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        requestFields(fieldWithPath("applicantIds").description("불합격하지 않은 지원자의 id"))
                ))
                .when().patch("/v1/applicants/unreject")
                .then().log().all().statusCode(400);
    }
}
