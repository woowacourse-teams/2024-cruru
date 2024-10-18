package com.cruru.auth.aspect;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.domain.repository.EvaluationRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.auth.controller.AuthTestDto;
import com.cruru.auth.service.AuthService;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.MemberFixture;
import com.cruru.util.fixture.ProcessFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("인가 AOP 테스트")
class AuthValidationAspectTest extends ControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    private ApplyForm applyForm;
    private Dashboard dashboard;
    private Process process;
    private Applicant applicant;
    private List<Evaluation> evaluations;
    private AuthTestDto authTestDto;
    private String unauthorizedToken;

    @BeforeEach
    void setUp() {
        Member unauthorizedMember = memberRepository.save(MemberFixture.RUSH);
        unauthorizedToken = authService.createAccessToken(unauthorizedMember).getToken();
        clubRepository.save(ClubFixture.create(unauthorizedMember));
        dashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        process = processRepository.save(ProcessFixture.applyType(dashboard));
        applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        evaluations = evaluationRepository.saveAll(List.of(
                EvaluationFixture.fivePoints(process, applicant),
                EvaluationFixture.fourPoints(process, applicant),
                EvaluationFixture.fourPoints(process, applicant)
        ));
        authTestDto = new AuthTestDto(
                process.getId(),
                applicant.getId(),
                evaluations.stream()
                        .map(Evaluation::getId)
                        .toList()
        );
    }

    @DisplayName("권한이 있는 사용자가 applyformId를 요청하면 성공한다.")
    @Test
    void testReadByRequestParam_Success() {
        RestAssured.given().log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .when().get("/auth-test/test1?applyformId=" + applyForm.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("권한이 없는 사용자가 applyformId를 요청하면 403 오류가 발생한다.")
    @Test
    void testReadByRequestParam_Forbidden() {

        RestAssured.given().log().all()
                .cookie("accessToken", unauthorizedToken)
                .contentType(ContentType.JSON)
                .when().get("/auth-test/test1?applyformId=" + applyForm.getId())
                .then().log().all().statusCode(403);
    }

    @DisplayName("권한이 있는 사용자가 PathVariable로 요청하면 성공한다.")
    @Test
    void testReadByPathVariable_Success() {
        RestAssured.given().log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .when().get("/auth-test/test2/" + applyForm.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("권한이 없는 사용자가 PathVariable로 요청하면 403 오류가 발생한다.")
    @Test
    void testReadByPathVariable_Forbidden() {
        RestAssured.given().log().all()
                .cookie("accessToken", unauthorizedToken)
                .contentType(ContentType.JSON)
                .when().get("/auth-test/test2/" + applyForm.getId())
                .then().log().all().statusCode(403);
    }

    @DisplayName("권한이 있는 사용자가 RequestBody로 요청하면 성공한다.")
    @Test
    void testReadByRequestBody_Success() {

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", token)
                .body(authTestDto)
                .when().get("/auth-test/test3")
                .then().log().all().statusCode(200);
    }

    @DisplayName("권한이 없는 사용자가 RequestBody로 요청하면 403 오류가 발생한다.")
    @Test
    void testReadByRequestBody_Forbidden() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", unauthorizedToken)
                .body(authTestDto)
                .when().get("/auth-test/test3")
                .then().log().all().statusCode(403);
    }

    @DisplayName("권한이 있는 사용자가 모든 Request 타입으로 요청하면 성공한다.")
    @Test
    void testReadByAllRequestType_Success() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", token)
                .body(authTestDto)
                .when().get("/auth-test/test4/" + dashboard.getId() + "?applyformId=" + applyForm.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("권한이 없는 사용자가 모든 Request 타입으로 요청하면 403 오류가 발생한다.")
    @Test
    void testReadByAllRequestType_Forbidden() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", unauthorizedToken)
                .body(authTestDto)
                .when().get("/auth-test/test4/" + dashboard.getId() + "?applyformId=" + applyForm.getId())
                .then().log().all().statusCode(403);
    }

    @DisplayName("권한이 있는 대상과 없는 대상을 섞어서 요청하면 403 오류가 발생한다..")
    @Test
    void readByAllRequestType_SomeOfForbidden() {
        Dashboard unauthorizedDashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        applyFormRepository.save(ApplyFormFixture.backend(unauthorizedDashboard));
        Process unauthorizedProcess = processRepository.save(ProcessFixture.applyType(unauthorizedDashboard));
        Applicant unauthorizedApplicant = applicantRepository.save(ApplicantFixture.pendingDobby(unauthorizedProcess));
        Evaluation unauthorizedEvaluation = evaluationRepository.save(EvaluationFixture.fivePoints(
                unauthorizedProcess,
                unauthorizedApplicant
        ));
        List<Long> authorizedEvaluationIds = new ArrayList<>(evaluations.stream()
                .map(Evaluation::getId)
                .toList());
        authorizedEvaluationIds.add(unauthorizedEvaluation.getId());
        AuthTestDto unauthorizedAuthTestDto = new AuthTestDto(
                process.getId(),
                applicant.getId(),
                authorizedEvaluationIds
        );

        evaluationRepository.saveAll(List.of(EvaluationFixture.fivePoints(process, applicant)));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", unauthorizedToken)
                .param("applyformId", applyForm.getId())
                .body(unauthorizedAuthTestDto)
                .when().get("/auth-test/test4/" + dashboard.getId() + "?applyformId=" + applyForm.getId())
                .then().log().all().statusCode(403);
    }
}
