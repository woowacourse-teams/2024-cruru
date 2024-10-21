package com.cruru.applicant.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.util.fixture.ApplicantCardFixture;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


@DisplayName("지원자 정렬 테스트")
class ApplicantSortOptionTest {

    @Nested
    @DisplayName("지원자의 지원 날짜 정렬 테스트")
    class getCreatedAtComparatorTest {

        @DisplayName("지원자의 지원 날짜 기준으로 오름차순으로 정렬한다.")
        @ParameterizedTest
        @ValueSource(strings = {"ASC", "asc"})
        void getCreatedAtComparator_asc(String sortByCreatedAt) {
            // given
            LocalDateTime createdAt1 = LocalDateTime.of(2024, 10, 2, 20, 0);
            LocalDateTime createdAt2 = LocalDateTime.of(2024, 10, 3, 20, 0);
            ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard(createdAt1);
            ApplicantCard applicantCard2 = ApplicantCardFixture.evaluatedApplicantCard(createdAt2);
            List<ApplicantCard> applicantCards = Arrays.asList(applicantCard2, applicantCard1);

            // when
            applicantCards.sort(ApplicantSortOption.getComparator(
                    sortByCreatedAt,
                    null
            ));

            // then
            assertThat(applicantCards).containsExactly(applicantCard1, applicantCard2);
        }

        @DisplayName("지원자의 지원 날짜 기준으로 내림차순으로 정렬한다.")
        @ParameterizedTest
        @ValueSource(strings = {"DESC", "desc"})
        void getCreatedAtComparator_desc(String sortByCreatedAt) {
            // given
            LocalDateTime createdAt1 = LocalDateTime.of(2024, 10, 2, 20, 0);
            LocalDateTime createdAt2 = LocalDateTime.of(2024, 10, 3, 20, 0);
            ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard(createdAt1);
            ApplicantCard applicantCard2 = ApplicantCardFixture.evaluatedApplicantCard(createdAt2);
            List<ApplicantCard> applicantCards = Arrays.asList(applicantCard1, applicantCard2);

            // when
            applicantCards.sort(ApplicantSortOption.getComparator(
                    sortByCreatedAt,
                    null
            ));

            // then
            assertThat(applicantCards).containsExactly(applicantCard2, applicantCard1);
        }
    }

    @Nested
    @DisplayName("지원자의 평균 점수 정렬 테스트")
    class getScoreComparatorTest {

        @DisplayName("지원자의 평균 점수 기준으로 오름차순으로 정렬한다.")
        @ParameterizedTest
        @ValueSource(strings = {"ASC", "asc"})
        void getScoreComparator_asc(String sortByScore) {
            // given
            LocalDateTime createdAt = LocalDateTime.of(2024, 10, 2, 20, 0);
            ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard(createdAt);
            ApplicantCard applicantCard2 = ApplicantCardFixture.evaluatedApplicantCard(createdAt);
            List<ApplicantCard> applicantCards = Arrays.asList(applicantCard1, applicantCard2);

            // when
            applicantCards.sort(ApplicantSortOption.getComparator(
                    null,
                    sortByScore
            ));

            // then
            assertThat(applicantCards).containsExactly(applicantCard2, applicantCard1);
        }

        @DisplayName("지원자의 평균 점수 기준으로 내림차순으로 정렬한다.")
        @ParameterizedTest
        @ValueSource(strings = {"DESC", "desc"})
        void getScoreComparator_desc(String sortByScore) {
            // given
            LocalDateTime createdAt = LocalDateTime.of(2024, 10, 2, 20, 0);
            ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard(createdAt);
            ApplicantCard applicantCard2 = ApplicantCardFixture.evaluatedApplicantCard(createdAt);
            List<ApplicantCard> applicantCards = Arrays.asList(applicantCard2, applicantCard1);

            // when
            applicantCards.sort(ApplicantSortOption.getComparator(
                    null,
                    sortByScore
            ));

            // then
            assertThat(applicantCards).containsExactly(applicantCard1, applicantCard2);
        }
    }
}
