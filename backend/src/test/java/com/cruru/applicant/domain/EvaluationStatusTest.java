package com.cruru.applicant.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.util.fixture.ApplicantCardFixture;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("지원자 평가 상태 필터링 테스트")
class EvaluationStatusTest {

    @Nested
    @DisplayName("ALL 상태 테스트")
    class AllStatusTest {

        private static Stream<Arguments> provideApplicantCardsForAllStatus() {
            return Stream.of(
                    Arguments.of(ApplicantCardFixture.evaluatedApplicantCard(), "ALL"),
                    Arguments.of(ApplicantCardFixture.notEvaluatedApplicantCard(), "ALL"),
                    Arguments.of(ApplicantCardFixture.evaluatedApplicantCard(), "all"),
                    Arguments.of(ApplicantCardFixture.notEvaluatedApplicantCard(), "all")
            );
        }

        @DisplayName("평가 상태가 ALL이면 항상 true를 반환한다.")
        @ParameterizedTest
        @MethodSource("provideApplicantCardsForAllStatus")
        void matchesEvaluationStatus_all(ApplicantCard card, String evaluationStatus) {
            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, evaluationStatus);

            // then
            assertThat(actual).isTrue();
        }
    }

    @Nested
    @DisplayName("NOT_EVALUATED 상태 테스트")
    class NotEvaluatedStatusTest {

        @DisplayName("평가 상태가 NOT_EVALUATED일 때, 평가 수가 0이면 true를 반환한다.")
        @ParameterizedTest
        @ValueSource(strings = {"NOT_EVALUATED", "not_evaluated"})
        void matchesEvaluationStatus_notEvaluated_true(String evaluationStatus) {
            // given
            ApplicantCard card = ApplicantCardFixture.notEvaluatedApplicantCard();

            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, evaluationStatus);

            // then
            assertThat(actual).isTrue();
        }

        @DisplayName("평가 상태가 NOT_EVALUATED일 때, 평가 수가 0보다 크면 false를 반환한다.")
        @ParameterizedTest
        @ValueSource(strings = {"NOT_EVALUATED", "not_evaluated"})
        void matchesEvaluationStatus_notEvaluated_false(String evaluationStatus) {
            // given
            ApplicantCard card = ApplicantCardFixture.evaluatedApplicantCard();

            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, evaluationStatus);

            // then
            assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("EVALUATED 상태 테스트")
    class EvaluatedStatusTest {

        @DisplayName("평가 상태가 EVALUATED일 때, 평가 수가 0보다 크면 true를 반환한다.")
        @ParameterizedTest
        @ValueSource(strings = {"EVALUATED", "evaluated"})
        void matchesEvaluationStatus_evaluated_true(String evaluationStatus) {
            // given
            ApplicantCard card = ApplicantCardFixture.evaluatedApplicantCard();

            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, evaluationStatus);

            // then
            assertThat(actual).isTrue();
        }

        @DisplayName("평가 상태가 EVALUATED일 때, 평가 수가 0이면 false를 반환한다.")
        @ParameterizedTest
        @ValueSource(strings = {"EVALUATED", "evaluated"})
        void matchesEvaluationStatus_evaluated_false(String evaluationStatus) {
            // given
            ApplicantCard card = ApplicantCardFixture.notEvaluatedApplicantCard();

            // when
            boolean actual = EvaluationStatus.matchesEvaluationStatus(card, evaluationStatus);

            // then
            assertThat(actual).isFalse();
        }
    }
}
