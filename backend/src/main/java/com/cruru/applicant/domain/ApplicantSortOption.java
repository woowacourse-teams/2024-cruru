package com.cruru.applicant.domain;

import com.cruru.applicant.domain.dto.ApplicantCard;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public enum ApplicantSortOption {

    ASC(Comparator.naturalOrder()),
    DESC(Comparator.reverseOrder());

    private final Comparator<Comparable> comparator;

    ApplicantSortOption(Comparator<Comparable> comparator) {
        this.comparator = comparator;
    }

    public static Comparator<ApplicantCard> getComparator(String sortByCreatedAt, String sortByScore) {
        Optional<ApplicantSortOption> createdAtOption = convertToSortOption(sortByCreatedAt);
        Optional<ApplicantSortOption> scoreOption = convertToSortOption(sortByScore);

        if (createdAtOption.isPresent()) {
            return createdAtOption.get().getCreatedAtComparator();
        }
        if (scoreOption.isPresent()) {
            return scoreOption.get().getScoreComparator();
        }
        return Comparator.comparing(ApplicantCard::createdAt, DESC.comparator);
    }

    private static Optional<ApplicantSortOption> convertToSortOption(String sortOption) {
        if (sortOption == null || sortOption.isEmpty()) {
            return Optional.empty();
        }
        return Arrays.stream(ApplicantSortOption.values())
                .filter(option -> option.name().equalsIgnoreCase(sortOption))
                .findAny();
    }

    private Comparator<ApplicantCard> getCreatedAtComparator() {
        return Comparator.comparing(ApplicantCard::createdAt, comparator);
    }

    private Comparator<ApplicantCard> getScoreComparator() {
        return Comparator.comparing(ApplicantCard::averageScore, comparator);
    }
}
