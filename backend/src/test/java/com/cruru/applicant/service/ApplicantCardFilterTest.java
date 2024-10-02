package com.cruru.applicant.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.applicant.domain.EvaluationStatus;
import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.util.fixture.ApplicantCardFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("지원자 카드 filter 테스트")
class ApplicantCardFilterTest {

    @DisplayName("평균 점수가 범위 안에 있으면 true를 반환한다.")
    @Test
    void filterByScore_returnTrue() {
        // given
        ApplicantCard card = ApplicantCardFixture.evaluatedApplicantCard(null);

        // when
        boolean actual = ApplicantCardFilter.filterByScore(card, 0.00, 4.50);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("평균 점수가 범위 안에 없으면 false를 반환한다.")
    @Test
    void filterByScore_returnFalse() {
        // given
        ApplicantCard card = ApplicantCardFixture.notEvaluatedApplicantCard();

        // when
        boolean actual = ApplicantCardFilter.filterByScore(card, 4.00, 4.50);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("평가 상태와 상관없이 모든 지원자를 필터링한다.")
    @Test
    void filterByEvaluationStatus_all() {
        // given
        ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard();
        ApplicantCard applicantCard2 = ApplicantCardFixture.notEvaluatedApplicantCard();

        // when
        boolean actual1 = ApplicantCardFilter.filterByEvaluationStatus(applicantCard1, EvaluationStatus.ALL);
        boolean actual2 = ApplicantCardFilter.filterByEvaluationStatus(applicantCard2, EvaluationStatus.ALL);

        // then
        assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isTrue()
        );
    }

    @DisplayName("평가가 없는 지원자만 필터링 한다.")
    @Test
    void filterByEvaluationStatus_notEvaluated() {
        // given
        ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard();
        ApplicantCard applicantCard2 = ApplicantCardFixture.notEvaluatedApplicantCard();

        // when
        boolean actual1 = ApplicantCardFilter.filterByEvaluationStatus(applicantCard1, EvaluationStatus.NOT_EVALUATED);
        boolean actual2 = ApplicantCardFilter.filterByEvaluationStatus(applicantCard2, EvaluationStatus.NOT_EVALUATED);

        // then
        assertAll(
                () -> assertThat(actual1).isFalse(),
                () -> assertThat(actual2).isTrue()
        );
    }

    @DisplayName("평가가 있는 지원자만 필터링한다.")
    @Test
    void filterByEvaluationStatus_evaluated() {
        // given
        ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard();
        ApplicantCard applicantCard2 = ApplicantCardFixture.notEvaluatedApplicantCard();

        // when
        boolean actual1 = ApplicantCardFilter.filterByEvaluationStatus(applicantCard1, EvaluationStatus.EVALUATED);
        boolean actual2 = ApplicantCardFilter.filterByEvaluationStatus(applicantCard2, EvaluationStatus.EVALUATED);

        // then
        assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isFalse()
        );
    }
}
