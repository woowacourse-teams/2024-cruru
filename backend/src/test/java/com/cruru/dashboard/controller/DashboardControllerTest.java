package com.cruru.dashboard.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.applicant.domain.repository.EvaluationRepository;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.controller.request.DashboardCreateRequest;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.controller.request.ChoiceCreateRequest;
import com.cruru.question.controller.request.QuestionCreateRequest;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.AnswerRepository;
import com.cruru.question.domain.repository.ChoiceRepository;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.AnswerFixture;
import com.cruru.util.fixture.ApplicantFixture;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ChoiceFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.EmailFixture;
import com.cruru.util.fixture.EvaluationFixture;
import com.cruru.util.fixture.LocalDateFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.FieldDescriptor;

@DisplayName("대시보드 컨트롤러 테스트")
class DashboardControllerTest extends ControllerTest {

    private static final FieldDescriptor[] DASHBOARD_PREVIEW_FIELD_DESCRIPTORS = {
            fieldWithPath("dashboardId").description("대시보드의 id"),
            fieldWithPath("applyFormId").description("지원폼의 id"),
            fieldWithPath("title").description("지원폼의 공고명"),
            fieldWithPath("stats.accept").description("합격 인원"),
            fieldWithPath("stats.fail").description("불합격 인원"),
            fieldWithPath("stats.inProgress").description("진행중 인원"),
            fieldWithPath("stats.total").description("인원 총계"),
            fieldWithPath("startDate").description("시작날짜"),
            fieldWithPath("endDate").description("마감날짜")
    };

    private static final FieldDescriptor[] QUESTION_FIELD_DESCRIPTORS = {
            fieldWithPath("type").description("질문 유형"),
            fieldWithPath("question").description("질문 내용"),
            fieldWithPath("choices").description("질문의 선택지들"),
            fieldWithPath("orderIndex").description("질문의 순서"),
            fieldWithPath("required").description("질문의 필수여부")
    };

    private static final FieldDescriptor[] CHOICE_FIELD_DESCRIPTORS = {
            fieldWithPath("choice").description("객관식 질문의 선택지"),
            fieldWithPath("orderIndex").description("선택지의 순서")
    };

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    private static Stream<QuestionCreateRequest> InvalidQuestionCreateRequest() {
        String validChoice = "선택지1";
        int validOrderIndex = 0;
        List<ChoiceCreateRequest> validChoiceCreateRequests
                = List.of(new ChoiceCreateRequest(validChoice, validOrderIndex));
        String validType = "DROPDOWN";
        String validQuestion = "객관식질문";
        boolean validRequired = false;
        return Stream.of(
                new QuestionCreateRequest(null, validQuestion,
                        validChoiceCreateRequests, validOrderIndex, validRequired
                ),
                new QuestionCreateRequest("", validQuestion,
                        validChoiceCreateRequests, validOrderIndex, validRequired
                ),
                new QuestionCreateRequest(validType, null,
                        validChoiceCreateRequests, validOrderIndex, validRequired
                ),
                new QuestionCreateRequest(validType, "",
                        validChoiceCreateRequests, validOrderIndex, validRequired
                ),
                new QuestionCreateRequest(validType, validQuestion,
                        List.of(new ChoiceCreateRequest(null, validOrderIndex)), validOrderIndex, validRequired
                ),
                new QuestionCreateRequest(validType, validQuestion,
                        List.of(new ChoiceCreateRequest("", validOrderIndex)), validOrderIndex, validRequired
                ),
                new QuestionCreateRequest(validType, validQuestion,
                        List.of(new ChoiceCreateRequest(validChoice, null)), validOrderIndex, validRequired
                ),
                new QuestionCreateRequest(validType, validQuestion,
                        List.of(new ChoiceCreateRequest(validChoice, -1)), validOrderIndex, validRequired
                ),
                new QuestionCreateRequest(validType, validQuestion,
                        validChoiceCreateRequests, null, validRequired
                ),
                new QuestionCreateRequest(validType, validQuestion,
                        validChoiceCreateRequests, -1, validRequired
                ),
                new QuestionCreateRequest(validType, validQuestion,
                        validChoiceCreateRequests, validOrderIndex, null
                )
        );
    }

    @DisplayName("대시보드 생성 성공 시, 201을 응답한다.")
    @Test
    void create() {
        // given
        List<ChoiceCreateRequest> choiceCreateRequests = List.of(new ChoiceCreateRequest("선택지1", 1));
        List<QuestionCreateRequest> questionCreateRequests = List.of(
                new QuestionCreateRequest("DROPDOWN", "객관식질문1", choiceCreateRequests, 1, false));
        DashboardCreateRequest request = new DashboardCreateRequest(
                "크루루대시보드",
                "# 공고 내용",
                questionCreateRequests,
                LocalDateFixture.oneDayLater(),
                LocalDateFixture.oneWeekLater()
        );
        String url = String.format("/v1/dashboards?clubId=%d", defaultClub.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", token)
                .body(request)
                .filter(document(
                        "dashboard/create",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("clubId").description("동아리의 id")),
                        requestFields(
                                fieldWithPath("title").description("공고 제목"),
                                fieldWithPath("postingContent").description("공고 내용"),
                                fieldWithPath("questions").description("질문들"),
                                fieldWithPath("startDate").description("공고 시작 날짜"),
                                fieldWithPath("endDate").description("공고 마감 날짜")
                        ).andWithPrefix("questions[].", QUESTION_FIELD_DESCRIPTORS)
                                .andWithPrefix("questions[].choices[].", CHOICE_FIELD_DESCRIPTORS),
                        responseFields(
                                fieldWithPath("applyFormId").description("지원폼의 id"),
                                fieldWithPath("dashboardId").description("대시보드의 id")
                        )
                ))
                .when().post(url)
                .then().log().all().statusCode(201);
    }

    @DisplayName("대시보드 생성 시, 질문 생성 요청이 잘못되면 400을 응답한다.")
    @ParameterizedTest
    @MethodSource("InvalidQuestionCreateRequest")
    void create_invalidQuestionCreateRequest(QuestionCreateRequest invalidQuestionCreateRequest) {
        // given
        List<QuestionCreateRequest> questionCreateRequests = List.of(invalidQuestionCreateRequest);
        DashboardCreateRequest request = new DashboardCreateRequest(
                "크루루대시보드",
                "# 공고 내용",
                questionCreateRequests,
                LocalDateFixture.oneDayLater(),
                LocalDateFixture.oneWeekLater()
        );
        String url = String.format("/v1/dashboards?clubId=%d", defaultClub.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", token)
                .body(request)
                .filter(document(
                        "dashboard/create-fail/invalid-question",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("clubId").description("동아리의 id")),
                        requestFields(
                                fieldWithPath("title").description("공고 제목"),
                                fieldWithPath("postingContent").description("공고 내용"),
                                fieldWithPath("questions").description("질문들"),
                                fieldWithPath("startDate").description("공고 시작 날짜"),
                                fieldWithPath("endDate").description("공고 마감 날짜")
                        ).andWithPrefix("questions[].", QUESTION_FIELD_DESCRIPTORS)
                                .andWithPrefix("questions[].choices[].", CHOICE_FIELD_DESCRIPTORS)
                ))
                .when().post(url)
                .then().log().all().statusCode(400);
    }

    @DisplayName("대시보드 생성 성공 시, 동아리가 존재하지 않으면 404를 응답한다.")
    @Test
    void create_invalidClub() {
        // given
        List<ChoiceCreateRequest> choiceCreateRequests = List.of(new ChoiceCreateRequest("선택지1", 1));
        List<QuestionCreateRequest> questionCreateRequests = List.of(
                new QuestionCreateRequest("DROPDOWN", "객관식질문1", choiceCreateRequests, 1, false));
        DashboardCreateRequest request = new DashboardCreateRequest(
                "크루루대시보드",
                "# 공고 내용",
                questionCreateRequests,
                LocalDateFixture.oneDayLater(),
                LocalDateFixture.oneWeekLater()
        );
        long invalidClubId = -1;
        String url = String.format("/v1/dashboards?clubId=%d", invalidClubId);

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", token)
                .body(request)
                .filter(document(
                        "dashboard/create-fail/club-not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("clubId").description("존재하지 않는 동아리 id")),
                        requestFields(
                                fieldWithPath("title").description("공고 제목"),
                                fieldWithPath("postingContent").description("공고 내용"),
                                fieldWithPath("questions").description("질문들"),
                                fieldWithPath("startDate").description("공고 시작 날짜"),
                                fieldWithPath("endDate").description("공고 마감 날짜")
                        ).andWithPrefix("questions[].", QUESTION_FIELD_DESCRIPTORS)
                                .andWithPrefix("questions[].choices[].", CHOICE_FIELD_DESCRIPTORS)
                ))
                .when().post(url)
                .then().log().all().statusCode(404);
    }

    @DisplayName("다건의 대시보드 요약 정보 요청 성공 시, 200을 응답한다")
    @Test
    void readDashboards_success() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        String url = String.format("/v1/dashboards?clubId=%d", defaultClub.getId());

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "dashboard/read",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("clubId").description("동아리의 id")),
                        responseFields(
                                fieldWithPath("clubName").description("동아리명"),
                                fieldWithPath("dashboards").description("대시보드들의 요약 정보")
                        ).andWithPrefix("dashboards[].", DASHBOARD_PREVIEW_FIELD_DESCRIPTORS)
                ))
                .when().get(url)
                .then().log().all().statusCode(200);
    }

    @DisplayName("대시보드 삭제 시, 204를 응답한다.")
    @Test
    void delete() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Question question = questionRepository.save(QuestionFixture.singleChoiceType(applyForm));
        choiceRepository.save(ChoiceFixture.first(question));
        Process process = processRepository.save(ProcessFixture.applyType(dashboard));
        Applicant applicant = applicantRepository.save(ApplicantFixture.pendingDobby(process));
        answerRepository.save(AnswerFixture.first(question, applicant));
        emailRepository.save(EmailFixture.rejectEmail(defaultClub, applicant));
        evaluationRepository.save(EvaluationFixture.fivePoints(process, applicant));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "dashboard/delete",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("dashboardId").description("삭제할 대시보드의 id"))
                ))
                .when().delete("/v1/dashboards/{dashboardId}", dashboard.getId())
                .then().log().all().statusCode(204);
    }

    @DisplayName("대시보드 삭제 시 대시보드가 존재하지 않으면, 404를 응답한다.")
    @Test
    void delete_notFound() {
        // given
        Long invalidId = -1L;

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .filter(document(
                        "dashboard/delete/not-found",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("dashboardId").description("존재하지 않는 대시보드의 id"))
                ))
                .when().delete("/v1/dashboards/{dashboardId}", invalidId)
                .then().log().all().statusCode(404);
    }
}
