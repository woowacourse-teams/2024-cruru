package com.cruru.applicant.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.util.fixture.ApplicantCardFixture;
import com.cruru.util.fixture.DefaultFilterAndOrderFixture;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("지원자 카드 sorter 테스트")
class ApplicantCardSorterTest {

    @DisplayName("지원자의 지원 날짜 기준으로 내림차순으로 정렬한다.")
    @Test
    void getCreatedAtComparator_desc() {
        // given
        LocalDateTime createdAt1 = LocalDateTime.of(2024, 10, 2, 20, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2024, 10, 3, 20, 0);
        ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard(createdAt1);
        ApplicantCard applicantCard2 = ApplicantCardFixture.evaluatedApplicantCard(createdAt2);
        List<ApplicantCard> applicantCards = Arrays.asList(applicantCard1, applicantCard2);

        // when
        applicantCards.sort(ApplicantCardSorter.getCombinedComparator(
                DefaultFilterAndOrderFixture.DEFAULT_SORT_BY_CREATED_AT,
                DefaultFilterAndOrderFixture.DEFAULT_SORT_BY_SCORE

        ));

        // then
        assertThat(applicantCards).containsExactly(applicantCard2, applicantCard1);
    }

    @DisplayName("지원자의 지원 날짜 기준으로 오름차순으로 정렬한다.")
    @Test
    void getCreatedAtComparator_asc() {
        // given
        LocalDateTime createdAt1 = LocalDateTime.of(2024, 10, 2, 20, 0);
        LocalDateTime createdAt2 = LocalDateTime.of(2024, 10, 3, 20, 0);
        ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard(createdAt1);
        ApplicantCard applicantCard2 = ApplicantCardFixture.evaluatedApplicantCard(createdAt2);
        List<ApplicantCard> applicantCards = Arrays.asList(applicantCard2, applicantCard1);

        // when
        applicantCards.sort(ApplicantCardSorter.getCombinedComparator(
                "asc",
                DefaultFilterAndOrderFixture.DEFAULT_SORT_BY_SCORE
        ));

        // then
        assertThat(applicantCards).containsExactly(applicantCard1, applicantCard2);
    }

    @DisplayName("지원자의 평균 점수 기준으로 내림차순으로 정렬한다.")
    @Test
    void getScoreComparator_desc() {
        // given
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 2, 20, 0);
        ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard(createdAt);
        ApplicantCard applicantCard2 = ApplicantCardFixture.evaluatedApplicantCard(createdAt);
        List<ApplicantCard> applicantCards = Arrays.asList(applicantCard2, applicantCard1);

        // when
        applicantCards.sort(ApplicantCardSorter.getCombinedComparator(
                DefaultFilterAndOrderFixture.DEFAULT_SORT_BY_CREATED_AT,
                "desc"
        ));

        // then
        assertThat(applicantCards).containsExactly(applicantCard1, applicantCard2);
    }

    @DisplayName("지원자의 평균 점수 기준으로 오름차순으로 정렬한다.")
    @Test
    void getScoreComparator_asc() {
        // given
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 2, 20, 0);
        ApplicantCard applicantCard1 = ApplicantCardFixture.evaluatedApplicantCard(createdAt);
        ApplicantCard applicantCard2 = ApplicantCardFixture.evaluatedApplicantCard(createdAt);
        List<ApplicantCard> applicantCards = Arrays.asList(applicantCard1, applicantCard2);

        // when
        applicantCards.sort(ApplicantCardSorter.getCombinedComparator(
                DefaultFilterAndOrderFixture.DEFAULT_SORT_BY_CREATED_AT,
                "asc"
        ));

        // then
        assertThat(applicantCards).containsExactly(applicantCard2, applicantCard1);
    }
}
