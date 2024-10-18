package com.cruru.question.controller;

import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.question.controller.request.ChoiceCreateRequest;
import com.cruru.question.controller.request.QuestionCreateRequest;
import com.cruru.question.controller.request.QuestionUpdateRequests;
import com.cruru.question.domain.QuestionType;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.QuestionFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.payload.FieldDescriptor;

@DisplayName("질문 컨트롤러 테스트")
class QuestionControllerTest extends ControllerTest {

    private static final FieldDescriptor[] QUESTION_FIELD_DESCRIPTORS = {
            fieldWithPath("type").description("질문의 유형"),
            fieldWithPath("question").description("질문 내용"),
            fieldWithPath("choices").description("질문의 선택지들"),
            fieldWithPath("orderIndex").description("질문의 순서"),
            fieldWithPath("required").description("질문의 필수 여부")
    };

    private static final FieldDescriptor[] CHOICE_FIELD_DESCRIPTORS = {
            fieldWithPath("choice").description("선택지의 내용"),
            fieldWithPath("orderIndex").description("선택지의 순서")
    };

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @DisplayName("존재하는 질문의 변경 성공시, 200을 응답한다.")
    @Test
    void update() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.notStarted());
        questionRepository.save(QuestionFixture.multipleChoiceType(applyForm));
        QuestionUpdateRequests questionUpdateRequests = new QuestionUpdateRequests(
                List.of(
                        new QuestionCreateRequest(
                                QuestionType.LONG_ANSWER.name(),
                                "new",
                                List.of(new ChoiceCreateRequest("좋아하는 음식은?", 0)),
                                0,
                                true
                        )
                )
        );

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(questionUpdateRequests)
                .filter(document("question/update",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("applyformId").description("질문을 변경할 지원폼의 id")),
                        requestFields(fieldWithPath("questions").description("변경할 질문들"))
                                .andWithPrefix("questions[].", QUESTION_FIELD_DESCRIPTORS)
                                .andWithPrefix("questions[].choices[].", CHOICE_FIELD_DESCRIPTORS)
                ))
                .when().patch("/v1/questions?applyformId={applyformId}", applyForm.getId())
                .then().log().all().statusCode(200);
    }

    @DisplayName("존재하는 않는 지원폼의 질문 변경 시, 404를 응답한다.")
    @Test
    void update_applyFormNotFound() {
        // given
        Long invalidApplyFormId = -1L;
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.notStarted());
        questionRepository.save(QuestionFixture.multipleChoiceType(applyForm));
        QuestionUpdateRequests questionUpdateRequests = new QuestionUpdateRequests(
                List.of(
                        new QuestionCreateRequest(
                                QuestionType.LONG_ANSWER.name(),
                                "new",
                                List.of(new ChoiceCreateRequest("좋아하는 음식은?", 0)),
                                0,
                                true
                        )
                )
        );

        // when&then
        RestAssured.given(spec).log().all()
                .cookie("accessToken", token)
                .contentType(ContentType.JSON)
                .body(questionUpdateRequests)
                .filter(document("question/update",
                        requestCookies(cookieWithName("accessToken").description("사용자 토큰")),
                        queryParameters(parameterWithName("applyformId").description("존재하지 않는 지원폼의 id")),
                        requestFields(fieldWithPath("questions").description("변경할 질문들"))
                                .andWithPrefix("questions[].", QUESTION_FIELD_DESCRIPTORS)
                                .andWithPrefix("questions[].choices[].", CHOICE_FIELD_DESCRIPTORS)
                ))
                .when().patch("/v1/questions?applyformId={applyformId}", invalidApplyFormId)
                .then().log().all().statusCode(404);
    }
}
