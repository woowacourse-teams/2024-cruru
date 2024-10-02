package com.cruru.applicant.service;

import com.cruru.applicant.domain.SortOption;
import com.cruru.applicant.domain.dto.ApplicantCard;
import java.util.Comparator;

public class ApplicantCardSorter {

    private ApplicantCardSorter() {

    }

    public static Comparator<ApplicantCard> getCombinedComparator(SortOption sortByCreatedAt, SortOption sortByScore) {
        return getCreatedAtComparator(sortByCreatedAt)
                .thenComparing(getScoreComparator(sortByScore));
    }

    private static Comparator<ApplicantCard> getCreatedAtComparator(SortOption sortByCreatedAt) {
        if (sortByCreatedAt == SortOption.ASC) {
            return Comparator.comparing(ApplicantCard::createdAt);
        }
        return Comparator.comparing(ApplicantCard::createdAt, Comparator.reverseOrder());
    }

    private static Comparator<ApplicantCard> getScoreComparator(SortOption sortByScore) {
        if (sortByScore == SortOption.ASC) {
            return Comparator.comparing(ApplicantCard::averageScore);
        }
        return Comparator.comparing(ApplicantCard::averageScore, Comparator.reverseOrder());
    }
}

