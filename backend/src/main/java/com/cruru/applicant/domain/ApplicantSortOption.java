package com.cruru.applicant.domain;

import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.applicant.exception.badrequest.ApplicantSortException;
import java.util.Arrays;
import java.util.Comparator;

public enum ApplicantSortOption {

    ASC(Comparator.naturalOrder()),
    DESC(Comparator.reverseOrder());

    private final Comparator<Comparable> comparator;

    ApplicantSortOption(Comparator<Comparable> comparator) {
        this.comparator = comparator;
    }

    public static Comparator<ApplicantCard> getCombinedComparator(String sortByCreatedAt, String sortByScore) {
        ApplicantSortOption createdAtOption = convertToSortOption(sortByCreatedAt);
        ApplicantSortOption scoreOption = convertToSortOption(sortByScore);

        return createdAtOption.getCreatedAtComparator()
                .thenComparing(scoreOption.getScoreComparator());
    }

    private static ApplicantSortOption convertToSortOption(String sortOption) {
        return Arrays.stream(ApplicantSortOption.values())
                .filter(option -> option.name().equalsIgnoreCase(sortOption))
                .findAny()
                .orElseThrow(ApplicantSortException::new);
    }

    private Comparator<ApplicantCard> getCreatedAtComparator() {
        return Comparator.comparing(ApplicantCard::createdAt, comparator);
    }

    private Comparator<ApplicantCard> getScoreComparator() {
        return Comparator.comparing(ApplicantCard::averageScore, comparator);
    }
}
