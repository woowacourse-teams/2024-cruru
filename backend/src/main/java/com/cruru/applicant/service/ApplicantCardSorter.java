package com.cruru.applicant.service;

import com.cruru.applicant.domain.dto.ApplicantCard;
import java.util.Comparator;

public class ApplicantCardSorter {

    private ApplicantCardSorter() {

    }

    public static Comparator<ApplicantCard> getCombinedComparator(String sortByCreatedAt, String sortByScore) {
        return getCreatedAtComparator(sortByCreatedAt)
                .thenComparing(getScoreComparator(sortByScore));
    }

    private static Comparator<ApplicantCard> getCreatedAtComparator(String sortByCreatedAt) {
        if ("asc".equalsIgnoreCase(sortByCreatedAt)) {
            return Comparator.comparing(ApplicantCard::createdAt);
        }
        return Comparator.comparing(ApplicantCard::createdAt, Comparator.reverseOrder());
    }

    private static Comparator<ApplicantCard> getScoreComparator(String sortByScore) {
        if ("asc".equalsIgnoreCase(sortByScore)) {
            return Comparator.comparing(ApplicantCard::averageScore);
        }
        return Comparator.comparing(ApplicantCard::averageScore, Comparator.reverseOrder());
    }
}

