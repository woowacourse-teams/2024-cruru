package com.cruru.dashboard.controller;

import com.cruru.applyform.domain.repository.ApplyFormRepository;
import com.cruru.choice.controller.dto.ChoiceCreateRequest;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.controller.dto.DashboardCreateRequest;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.question.controller.dto.QuestionCreateRequest;
import com.cruru.util.ControllerTest;
import com.cruru.util.fixture.ApplyFormFixture;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.DashboardFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("대시보드 컨트롤러 테스트")
class DashboardControllerTest extends ControllerTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ApplyFormRepository applyFormRepository;

    private Club club;

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
                        validChoiceCreateRequests, validOrderIndex, validRequired),
                new QuestionCreateRequest("", validQuestion,
                        validChoiceCreateRequests, validOrderIndex, validRequired),
                new QuestionCreateRequest(validType, null,
                        validChoiceCreateRequests, validOrderIndex, validRequired),
                new QuestionCreateRequest(validType, "",
                        validChoiceCreateRequests, validOrderIndex, validRequired),
                new QuestionCreateRequest(validType, validQuestion,
                        List.of(new ChoiceCreateRequest(null, validOrderIndex)), validOrderIndex, validRequired),
                new QuestionCreateRequest(validType, validQuestion,
                        List.of(new ChoiceCreateRequest("", validOrderIndex)), validOrderIndex, validRequired),
                new QuestionCreateRequest(validType, validQuestion,
                        List.of(new ChoiceCreateRequest(validChoice, null)), validOrderIndex, validRequired),
                new QuestionCreateRequest(validType, validQuestion,
                        List.of(new ChoiceCreateRequest(validChoice, -1)), validOrderIndex, validRequired),
                new QuestionCreateRequest(validType, validQuestion,
                        validChoiceCreateRequests, null, validRequired),
                new QuestionCreateRequest(validType, validQuestion,
                        validChoiceCreateRequests, -1, validRequired),
                new QuestionCreateRequest(validType, validQuestion,
                        validChoiceCreateRequests, validOrderIndex, null)
        );
    }

    @BeforeEach
    void setUp() {
        club = clubRepository.save(ClubFixture.create());
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
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2999, 12, 31, 23, 59)
        );
        String url = String.format("/v1/dashboards?clubId=%d", club.getId());

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
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
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2999, 12, 31, 23, 59)
        );
        String url = String.format("/v1/dashboards?clubId=%d", club.getId());

        // when&then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post(url)
                .then().log().all().statusCode(400);
    }

    @DisplayName("다건의 대시보드 요약 정보 요청 성공 시, 200을 응답한다")
    @Test
    void readDashboards_success() {
        // given
        Dashboard dashboard = dashboardRepository.save(DashboardFixture.backend(club));
        applyFormRepository.save(ApplyFormFixture.backend(dashboard));
        String url = String.format("/v1/dashboards?clubId=%d", club.getId());

        // when&then
        RestAssured.given().log().all()
                .when().get(url)
                .then().log().all().statusCode(200);
    }
}
