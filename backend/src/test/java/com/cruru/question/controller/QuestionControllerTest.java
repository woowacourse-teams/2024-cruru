package com.cruru.question.controller;

import com.cruru.applyform.domain.ApplyForm;
import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.question.controller.dto.QuestionUpdateRequests;
import com.cruru.question.domain.QuestionType;
import com.cruru.question.domain.repository.QuestionRepository;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.QuestionFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("질문 컨트롤러 테스트")
class QuestionControllerTest extends ControllerTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    @DisplayName("존재하는 질문의 변경 성공시, 200을 응답한다.")
    @Test
    void update() {
        // given
        ApplyForm applyForm = applyFormRepository.save(ApplyFormFixture.notStarted());
        questionRepository.save(QuestionFixture.shortAnswerType(applyForm));
        QuestionUpdateRequests questionUpdateRequests = new QuestionUpdateRequests(List.of(
                new QuestionCreateRequest(QuestionType.LONG_ANSWER.name(), "new", List.of(), 0, true)
        ));

        // when&then
        RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(questionUpdateRequests)
                .when().patch("/v1/questions?applyformId={applyformId}", applyForm.getId())
                .then().log().all().statusCode(200);
    }
}
