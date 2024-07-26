package com.cruru.applicant.controller;

import static com.cruru.util.fixture.ApplicantFixture.createApplicantDobby;
import static com.cruru.util.fixture.ApplicantFixture.createApplicantRush;
import static com.cruru.util.fixture.ApplicantFixture.createRejectedApplicantLurgi;
import static com.cruru.util.fixture.DashboardFixture.createBackendDashboard;
import static com.cruru.util.fixture.ProcessFixture.createFinalProcess;
import static com.cruru.util.fixture.ProcessFixture.createFirstProcess;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.answer.domain.Answer;
import com.cruru.answer.domain.repository.AnswerRepository;
import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.QuestionRepository;
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

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @DisplayName("지원자들의 프로세스를 일괄적으로 옮기는 데 성공하면 200을 응답한다.")
    @Test
    void updateApplicantProcess() {
        // given
        Process now = processRepository.save(createFirstProcess());
        Process next = processRepository.save(createFinalProcess());
        Applicant dobby = createApplicantDobby(now);
        Applicant rush = createApplicantRush(now);
        applicantRepository.saveAll(List.of(dobby, rush));

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(new ApplicantMoveRequest(List.of(dobby.getId(), rush.getId())))
                .filter(document("applicant/move-process/",
                        pathParameters(parameterWithName("process_id").description("지원자들이 옮겨질 프로세스의 id")),
                        requestFields(fieldWithPath("applicant_ids").description("프로세스를 옮길 지원자들의 id"))
                ))
                .when().put("/v1/applicants/move-process/{process_id}", next.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 프로세스로 지원자를 옮기려 시도하면 404를 응답한다.")
    @Test
    void updateApplicantProcess_processNotFound() {
        // given
        Process now = processRepository.save(createFirstProcess());
        Applicant applicant = createApplicantDobby(now);
        applicantRepository.save(applicant);
        long invalidProcessId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(new ApplicantMoveRequest(List.of(applicant.getId())))
                .filter(document("applicant/move-process-fail/process-not-found/",
                        pathParameters(parameterWithName("process_id").description("존재하지 않는 프로세스의 id")),
                        requestFields(fieldWithPath("applicant_ids").description("프로세스를 옮길 지원자들의 id"))
                ))
                .when().put("/v1/applicants/move-process/{process_id}", invalidProcessId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원자의 기본 정보를 읽어오는 데 성공하면 200을 응답한다.")
    @Test
    void read() {
        // given
        Process process = processRepository.save(createFirstProcess());
        Applicant applicant = applicantRepository.save(createApplicantDobby(process));

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("applicant/read-profile/",
                        pathParameters(parameterWithName("applicant_id").description("정보를 읽어올 지원자의 id")),
                        responseFields(
                                fieldWithPath("applicant_id").description("지원자의 id"),
                                fieldWithPath("name").description("지원자의 이름"),
                                fieldWithPath("email").description("지원자의 이메일"),
                                fieldWithPath("phone").description("지원자의 전화번호"),
                                fieldWithPath("created_at").description("지원자의 지원 날짜 및 시간"),
                                fieldWithPath("process_name").description("지원자의 현재 프로세스 이름")
                        )
                ))
                .when().get("/v1/applicants/{applicant_id}", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자의 기본 정보 읽어오기를 시도하면 404를 응답한다.")
    @Test
    void read_applicantNotFound() {
        // given
        long invalidApplicantId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("applicant/read-profile-fail/applicant-not-found/",
                        pathParameters(parameterWithName("applicant_id").description("존재하지 않는 지원자의 id"))
                ))
                .when().get("/v1/applicants/{applicant_id}", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원자의 상세 정보를 읽어오는 데 성공하면 200을 응답한다.")
    @Test
    void readDetail() {
        // given
        Dashboard dashboard = dashboardRepository.save(createBackendDashboard());
        Process process = processRepository.save(createFirstProcess(dashboard));
        Applicant applicant = applicantRepository.save(createApplicantDobby(process));
        Question question = questionRepository.save(new Question("나이", 0, dashboard));
        answerRepository.save(new Answer("28", question, applicant));

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("applicant/read-detail-profile/",
                        pathParameters(parameterWithName("applicant_id").description("정보를 읽어올 지원자의 id")),
                        responseFields(fieldWithPath("details").description("지원자의 질의응답"))
                                .andWithPrefix("details[].",
                                        fieldWithPath("order_index").description("질문 순서"),
                                        fieldWithPath("question").description("질문"),
                                        fieldWithPath("answer").description("응답"))
                ))
                .when().get("/v1/applicants/{applicant_id}/detail", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자의 상세 정보 읽어오기를 시도하면 404를 응답한다.")
    @Test
    void readDetail_applicantNotFound() {
        // given
        long invalidApplicantId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("applicant/read-detail-profile-fail/applicant-not-found/",
                        pathParameters(parameterWithName("applicant_id").description("존재하지 않는 지원자의 id"))
                ))
                .when().get("/v1/applicants/{applicant_id}/detail", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원자를 불합격시키는 데 성공하면 200을 응답한다.")
    @Test
    void reject() {
        // given
        Applicant applicant = applicantRepository.save(createApplicantDobby());

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("applicant/reject/",
                        pathParameters(parameterWithName("applicant_id").description("불합격시킬 지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicant_id}/reject", applicant.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하지 않는 지원자에 대해 불합격을 시도하면 404를 응답한다.")
    @Test
    void reject_applicantNotFound() {
        // given
        long invalidApplicantId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("applicant/reject-fail/applicant-not-found/",
                        pathParameters(parameterWithName("applicant_id").description("존재하지 않는 지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicant_id}/reject", invalidApplicantId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("이미 불합격한 지원자에 대해 불합격을 시도하면 400을 응답한다.")
    @Test
    void reject_alreadyRejected() {
        // given
        Applicant applicant = applicantRepository.save(createRejectedApplicantLurgi());

        // when&then
        RestAssured.given(spec).log().all()
                .filter(document("applicant/reject-fail/already-rejected/",
                        pathParameters(parameterWithName("applicant_id").description("이미 불합격인 지원자의 id"))
                ))
                .when().patch("/v1/applicants/{applicant_id}/reject", applicant.getId())
                .then().log().all().statusCode(400);
    }
}
