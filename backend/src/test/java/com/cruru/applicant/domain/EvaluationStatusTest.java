package com.cruru.applicant.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.util.fixture.ApplicantCardFixture;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("지원자 평가 상태 필터링 테스트")
class EvaluationStatusTest {

    @Nested
    @DisplayName("ALL 상태 테스트")
    class AllStatusTest {

        private static Stream<ApplicantCard> provideApplicantCardsForAllStatus() {
            return Stream.of(
                    ApplicantCardFixture.evaluatedApplicantCard(),
                    ApplicantCardFixture.notEvaluatedApplicantCard()
            );
        }

        @DisplayName("평가 상태가 ALL이면 항상 true를 반환한다.")
        @ParameterizedTest
        @MethodSource("provideApplicantCardsForAllStatus")
        void matchesEvaluationStatus_all(ApplicantCard card) {
            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, "ALL");

            // then
            assertThat(actual).isTrue();
        }
    }

    @Nested
    @DisplayName("NOT_EVALUATED 상태 테스트")
    class NotEvaluatedStatusTest {

        @DisplayName("평가 상태가 NOT_EVALUATED일 때, 평가 수가 0이면 true를 반환한다.")
        @Test
        void matchesEvaluationStatus_notEvaluated_true() {
            // given
            ApplicantCard card = ApplicantCardFixture.notEvaluatedApplicantCard();

            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, "NOT_EVALUATED");

            // then
            assertThat(actual).isTrue();
        }

        @DisplayName("평가 상태가 NOT_EVALUATED일 때, 평가 수가 0보다 크면 false를 반환한다.")
        @Test
        void matchesEvaluationStatus_notEvaluated_false() {
            // given
            ApplicantCard card = ApplicantCardFixture.evaluatedApplicantCard();

            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, "NOT_EVALUATED");

            // then
            assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("EVALUATED 상태 테스트")
    class EvaluatedStatusTest {

        @DisplayName("평가 상태가 EVALUATED일 때, 평가 수가 0보다 크면 true를 반환한다.")
        @Test
        void matchesEvaluationStatus_evaluated_true() {
            // given
            ApplicantCard card = ApplicantCardFixture.evaluatedApplicantCard();

            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, "EVALUATED");

            // then
            assertThat(actual).isTrue();
        }

        @DisplayName("평가 상태가 EVALUATED일 때, 평가 수가 0이면 false를 반환한다.")
        @Test
        void matchesEvaluationStatus_evaluated_false() {
            // given
            ApplicantCard card = ApplicantCardFixture.notEvaluatedApplicantCard();

            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, "EVALUATED");

            // then
            assertThat(actual).isFalse();
        }
    }
}
