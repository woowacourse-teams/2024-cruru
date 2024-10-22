package com.cruru.applyform.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.applicant.controller.request.ApplicantCreateRequest;
import com.cruru.applyform.controller.request.AnswerCreateRequest;
import com.cruru.applyform.controller.request.ApplyFormSubmitRequest;
import com.cruru.applyform.controller.request.ApplyFormWriteRequest;
import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.repository.ProcessRepository;
import com.cruru.question.domain.Question;
import com.cruru.question.domain.repository.ChoiceRepository;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ChoiceFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.LocalDateFixture;
import com.cruru.util.fixture.ProcessFixture;
import com.cruru.util.fixture.QuestionFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.FieldDescriptor;

@DisplayName("지원서 폼 컨트롤러 테스트")
class ApplyFormControllerTest extends ControllerTest {

    private static final FieldDescriptor[] ANSWER_SUBMIT_FIELD_DESCRIPTORS = {
            fieldWithPath("questionId").description("질문의 id"),
            fieldWithPath("replies").description("질문에 대한 응답")
    };

    private static final FieldDescriptor[] QUESTION_FIELD_DESCRIPTORS = {
            fieldWithPath("id").description("질문의 id"),
            fieldWithPath("type").description("질문 유형"),
            fieldWithPath("label").description("질문의 내용"),
            fieldWithPath("orderIndex").description("질문 순서"),
            fieldWithPath("choices").description("질문의 선택지"),
            fieldWithPath("required").description("질문 필수여부"),
            };

    private static final FieldDescriptor[] CHOICE_FIELD_DESCRIPTORS = {
            fieldWithPath("id").description("선택지의 id"),
            fieldWithPath("label").description("선택지의 내용"),
            fieldWithPath("orderIndex").description("선택지 순서")
    };

    private static final FieldDescriptor[] APPLICANT_SUBMIT_FIELD_DESCRIPTORS = {
            fieldWithPath("applicant.name").description("지원자의 이름"),
            fieldWithPath("applicant.email").description("지원자의 이메일"),
            fieldWithPath("applicant.phone").description("지원자의 전화번호"),
            fieldWithPath("answers").description("지원폼에 대한 응답 모음"),
            fieldWithPath("personalDataCollection").description("개인정보 활용 동의 여부")
    };

    private static final FieldDescriptor[] APPLYFORM_WRITE_FIELD_DESCRIPTORS = {
            fieldWithPath("title").description("지원폼 제목"),
            fieldWithPath("postingContent").description("지원폼 내용(본문)"),
            fieldWithPath("startDate").description("지원 시작 날짜"),
            fieldWithPath("endDate").description("지원 마감 날짜")
    };

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    private static Stream<ApplicantCreateRequest> InvalidApplicantCreateRequest() {
        String validName = "초코칩";
        String validMail = "dev.chocochip@gmail.com";
        String validPhone = "01000000000";
        return Stream.of(
                new ApplicantCreateRequest(null, validMail, validPhone),
                new ApplicantCreateRequest("", validMail, validPhone),
                new ApplicantCreateRequest(validName, null, validPhone),
                new ApplicantCreateRequest(validName, "", validPhone),
                new ApplicantCreateRequest(validName, "notMail", validPhone),
                new ApplicantCreateRequest(validName, validMail, null),
                new ApplicantCreateRequest(validName, validMail, "")
        );
    }

    @DisplayName("지원서 폼 제출 시, 201을 반환한다.")
    @Test
    void submit() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.applyType(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.frontend(dashboard));
        Question question1 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));
        Question question2 = questionRepository.save(QuestionFixture.longAnswerType(applyForm));

        List<AnswerCreateRequest> answerCreateRequests = List.of(
                new AnswerCreateRequest(question1.getId(), List.of("안녕하세요, 맛있는 초코칩입니다.")),
                new AnswerCreateRequest(question2.getId(), List.of("온라인"))
        );
        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                answerCreateRequests,
                true
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applyform/submit",
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        requestFields(APPLICANT_SUBMIT_FIELD_DESCRIPTORS)
                                .andWithPrefix("answers[].", ANSWER_SUBMIT_FIELD_DESCRIPTORS)
                ))
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(201);
    }

    @DisplayName("지원서 폼 제출 시, 개인정보 활용을 거부할 경우 400을 반환한다.")
    @Test
    void submit_rejectPersonalDataCollection() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.applyType(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.frontend(dashboard));
        Question question1 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));
        Question question2 = questionRepository.save(QuestionFixture.longAnswerType(applyForm));

        List<AnswerCreateRequest> answerCreateRequests = List.of(
                new AnswerCreateRequest(question1.getId(), List.of("안녕하세요, 맛있는 초코칩입니다.")),
                new AnswerCreateRequest(question2.getId(), List.of("온라인"))
        );
        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                answerCreateRequests,
                false
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applicant/submit-fail/reject-personal-data-collection",
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        requestFields(
                                fieldWithPath("applicant.name").description("지원자의 이름"),
                                fieldWithPath("applicant.email").description("지원자의 이메일"),
                                fieldWithPath("applicant.phone").description("지원자의 전화번호"),
                                fieldWithPath("answers").description("지원폼에 대한 응답 모음"),
                                fieldWithPath("personalDataCollection").description("개인정보 활용 동의 거부")
                        ).andWithPrefix("answers[].", ANSWER_SUBMIT_FIELD_DESCRIPTORS)
                ))
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원서 폼 제출 시, 지원자 정보가 잘못된 경우 400 에러가 발생한다.")
    @ParameterizedTest
    @MethodSource("InvalidApplicantCreateRequest")
    void submit_invalidApplicantCreateRequest(ApplicantCreateRequest applicantCreateRequest) {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.applyType(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.frontend(dashboard));
        Question question1 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        List<AnswerCreateRequest> answerCreateRequests = List.of(
                new AnswerCreateRequest(question1.getId(), List.of("안녕하세요, 맛있는 초코칩입니다."))
        );
        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                applicantCreateRequest,
                answerCreateRequests,
                true
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applicant/submit-fail/invalid-applicant-info",
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        requestFields(APPLICANT_SUBMIT_FIELD_DESCRIPTORS)
                                .andWithPrefix("answers[].", ANSWER_SUBMIT_FIELD_DESCRIPTORS)
                ))
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원서 폼 제출 시, 지원자 답변이 잘못된 경우 400 에러가 발생한다.")
    @Test
    void submit_invalidAnswerCreateRequests() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.applyType(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.frontend(dashboard));
        Question question1 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        List<AnswerCreateRequest> answerCreateRequests = List.of(
                new AnswerCreateRequest(question1.getId(), null)
        );
        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                answerCreateRequests,
                true
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applicant/submit-fail/invalid-answers",
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        requestFields(APPLICANT_SUBMIT_FIELD_DESCRIPTORS)
                                .andWithPrefix("answers[].", ANSWER_SUBMIT_FIELD_DESCRIPTORS)
                ))
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원서 폼 제출 시, 대시보드에 제출 프로세스가 존재하지 않으면 500을 반환한다.")
    @Test
    void submit_dashboardWithNoSubmitProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.frontend(dashboard));
        Question question = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                List.of(new AnswerCreateRequest(question.getId(), List.of("온라인"))),
                true
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applicant/submit-fail/no-submit-process",
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        requestFields(APPLICANT_SUBMIT_FIELD_DESCRIPTORS).
                                andWithPrefix("answers[].", ANSWER_SUBMIT_FIELD_DESCRIPTORS)
                ))
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(500);
    }

    @DisplayName("지원서 폼 제출 시, 모집 기간을 벗어난 경우 400을 반환한다.")
    @Test
    void submit_dateOutOfRange() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.notStarted());
        Question question = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                List.of(new AnswerCreateRequest(question.getId(), List.of("온라인"))),
                true
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applicant/submit-fail/date-out-of-range",
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        requestFields(APPLICANT_SUBMIT_FIELD_DESCRIPTORS)
                                .andWithPrefix("answers[].", ANSWER_SUBMIT_FIELD_DESCRIPTORS)
                ))
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원서 폼 제출 시, 지원서 폼이 존재하지 않을 경우 404를 반환한다.")
    @Test
    void submit_applyFormNotFound() {
        // given
        int invalidApplyFormId = -1;
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.notStarted());
        Question question = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));

        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                List.of(new AnswerCreateRequest(question.getId(), List.of("온라인"))),
                true
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applicant/submit-fail/applyform-not-found",
                        pathParameters(parameterWithName("applyFormId").description("존재하지 않는 지원폼의 id")),
                        requestFields(APPLICANT_SUBMIT_FIELD_DESCRIPTORS)
                                .andWithPrefix("answers[].", ANSWER_SUBMIT_FIELD_DESCRIPTORS)
                ))
                .when().post("/v1/applyform/{applyFormId}/submit", invalidApplyFormId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원서 폼 제출 시, 질문이 존재하지 않을 경우 400를 반환한다.")
    @Test
    void submit_questionNotFound() {
        // given
        long invalidQuestionId = -1;
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.notStarted());

        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                List.of(new AnswerCreateRequest(invalidQuestionId, List.of("온라인"))),
                true
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applicant/submit-fail/question-not-found",
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        requestFields(APPLICANT_SUBMIT_FIELD_DESCRIPTORS)
                                .andWithPrefix("answers[].",
                                        fieldWithPath("questionId").description("존재하지 않는 질문의 id"),
                                        fieldWithPath("replies").description("질문에 대한 응답")
                                )
                ))
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원서 폼 제출 시, 필수 질문에 응답하지 않은 경우 400를 반환한다.")
    @Test
    void submit_RequiredNotReplied() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.applyType(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        questionRepository.save(QuestionFixture.required(applyForm));

        ApplyFormSubmitRequest request = new ApplyFormSubmitRequest(
                new ApplicantCreateRequest("초코칩", "dev.chocochip@gmail.com", "01000000000"),
                List.of(),
                true
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .filter(document("applicant/submit-fail/required-not-replied",
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        requestFields(APPLICANT_SUBMIT_FIELD_DESCRIPTORS)
                ))
                .when().post("/v1/applyform/{applyFormId}/submit", applyForm.getId())
                .then().log().all().statusCode(400);
    }

    @DisplayName("지원서 폼 조회 시, 200을 반환한다.")
    @Test
    void read() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        processRepository.save(ProcessFixture.applyType(dashboard));
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        Question question1 = questionRepository.save(QuestionFixture.shortAnswerType(applyForm));
        Question question2 = questionRepository.save(QuestionFixture.multipleChoiceType(applyForm));
        choiceRepository.saveAll(ChoiceFixture.fiveChoices(question2));

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .filter(document("applicant/read-applyform",
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        responseFields(
                                fieldWithPath("title").description("지원폼의 제목"),
                                fieldWithPath("postingContent").description("지원자의 내용(본문)"),
                                fieldWithPath("startDate").description("지원 가능 날짜"),
                                fieldWithPath("endDate").description("지원 마감 날짜"),
                                fieldWithPath("questions").description("지원폼의 질문들")
                        ).andWithPrefix("questions[].", QUESTION_FIELD_DESCRIPTORS)
                                .andWithPrefix("questions[].choices[].", CHOICE_FIELD_DESCRIPTORS)
                ))
                .when().get("/v1/applyform/{applyFormId}", applyForm.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원서 폼 조회 시, 지원서 폼이 존재하지 않을 경우 404을 반환한다.")
    @Test
    void read_notFound() {
        // given
        int invalidApplyFormId = -1;

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", token)
                .filter(document("applicant/read-applyform-fail/applyform-not-found",
                        pathParameters(parameterWithName("applyFormId").description("존재하지 않는 지원폼의 id"))
                ))
                .when().get("/v1/applyform/{applyFormId}", invalidApplyFormId)
                .then().log().all().statusCode(404);
    }

    @DisplayName("지원서 폼을 성공적으로 수정하면, 200을 응답한다.")
    @Test
    void update() {
        // given
        String toChangeTitle = "크루루 백엔드 모집 공고~~";
        String toChangeDescription = "# 모집 공고 설명 #";
        LocalDateTime toChangeStartDate = LocalDateFixture.oneDayLater();
        LocalDateTime toChangeEndDate = LocalDateFixture.oneWeekLater();
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend());
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        ApplyFormWriteRequest request = new ApplyFormWriteRequest(
                toChangeTitle, toChangeDescription, toChangeStartDate, toChangeEndDate
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", token)
                .body(request)
                .filter(document("applicant/update",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        pathParameters(parameterWithName("applyFormId").description("지원폼의 id")),
                        requestFields(APPLYFORM_WRITE_FIELD_DESCRIPTORS)
                ))
                .when().patch("/v1/applyform/{applyFormId}", applyForm.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("지원서 폼 변경 시, 지원서 폼이 존재하지 않을 경우 404을 반환한다.")
    @Test
    void update_notFound() {
        // given
        int invalidApplyFormId = -1;
        String toChangeTitle = "크루루 백엔드 모집 공고~~";
        String toChangeDescription = "# 모집 공고 설명 #";
        LocalDateTime toChangeStartDate = LocalDateFixture.oneDayLater();
        LocalDateTime toChangeEndDate = LocalDateFixture.oneWeekLater();
        ApplyFormWriteRequest request = new ApplyFormWriteRequest(
                toChangeTitle, toChangeDescription, toChangeStartDate, toChangeEndDate
        );

        // when&then
        RestAssured.given(spec).log().all()
                .contentType(ContentType.JSON)
                .cookie("accessToken", token)
                .body(request)
                .filter(document("applicant/update-fail/applyform-not-found",
                        pathParameters(parameterWithName("applyFormId").description("존재하지 않는 지원폼의 id")),
                        requestFields(APPLYFORM_WRITE_FIELD_DESCRIPTORS)
                ))
                .when().patch("/v1/applyform/{applyFormId}", invalidApplyFormId)
                .then().log().all().statusCode(404);
    }
}
