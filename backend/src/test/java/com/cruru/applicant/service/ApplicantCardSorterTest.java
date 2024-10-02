package com.cruru.applicant.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.domain.dto.ApplicantCard;
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
        ApplicantCard applicantCard1 = new ApplicantCard(1L, "최냥인",
                LocalDateTime.of(2024, 10, 2, 20, 0), false, 5, 3.00, 1L);
        ApplicantCard applicantCard2 = new ApplicantCard(2L, "최냥인",
                LocalDateTime.of(2024, 10, 3, 20, 0), false, 0, 0.00, 1L);
        List<ApplicantCard> applicantCards = Arrays.asList(applicantCard1, applicantCard2);
        String defaultSortByScore = "desc";

        // when
        applicantCards.sort(ApplicantCardSorter.getCombinedComparator("desc", defaultSortByScore));

        // then
        assertThat(applicantCards).containsExactly(applicantCard2, applicantCard1);
    }

    @DisplayName("지원자의 지원 날짜 기준으로 오름차순으로 정렬한다.")
    @Test
    void getCreatedAtComparator_asc() {
        // given
        ApplicantCard applicantCard1 = new ApplicantCard(1L, "최냥인",
                LocalDateTime.of(2024, 10, 2, 20, 0), false, 5, 3.00, 1L);
        ApplicantCard applicantCard2 = new ApplicantCard(2L, "최냥인",
                LocalDateTime.of(2024, 10, 3, 20, 0), false, 0, 0.00, 1L);
        List<ApplicantCard> applicantCards = Arrays.asList(applicantCard2, applicantCard1);
        String defaultSortByScore = "desc";

        // when
        applicantCards.sort(ApplicantCardSorter.getCombinedComparator("asc", defaultSortByScore));

        // then
        assertThat(applicantCards).containsExactly(applicantCard1, applicantCard2);
    }

    @DisplayName("지원자의 평균 점수 기준으로 내림차순으로 정렬한다.")
    @Test
    void getScoreComparator_desc() {
        // given
        ApplicantCard applicantCard1 = new ApplicantCard(1L, "최냥인",
                LocalDateTime.of(2024, 10, 2, 20, 0), false, 5, 3.00, 1L);
        ApplicantCard applicantCard2 = new ApplicantCard(2L, "최냥인",
                LocalDateTime.of(2024, 10, 2, 20, 0), false, 0, 0.00, 1L);
        List<ApplicantCard> applicantCards = Arrays.asList(applicantCard2, applicantCard1);
        String defaultSortByCreatedAt = "desc";

        // when
        applicantCards.sort(ApplicantCardSorter.getCombinedComparator(defaultSortByCreatedAt, "desc"));

        // then
        assertThat(applicantCards).containsExactly(applicantCard1, applicantCard2);
    }

    @DisplayName("지원자의 평균 점수 기준으로 오름차순으로 정렬한다.")
    @Test
    void getScoreComparator_asc() {
        // given
        ApplicantCard applicantCard1 = new ApplicantCard(1L, "최냥인",
                LocalDateTime.of(2024, 10, 2, 20, 0), false, 5, 3.00, 1L);
        ApplicantCard applicantCard2 = new ApplicantCard(2L, "최냥인",
                LocalDateTime.of(2024, 10, 2, 20, 0), false, 0, 0.00, 1L);
        List<ApplicantCard> applicantCards = Arrays.asList(applicantCard1, applicantCard2);
        String defaultSortByCreatedAt = "desc";

        // when
        applicantCards.sort(ApplicantCardSorter.getCombinedComparator(defaultSortByCreatedAt, "asc"));

        // then
        assertThat(applicantCards).containsExactly(applicantCard2, applicantCard1);
    }
}
