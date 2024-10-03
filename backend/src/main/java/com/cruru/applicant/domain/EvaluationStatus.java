package com.cruru.applicant.domain;

import com.cruru.applicant.domain.dto.ApplicantCard;
import com.cruru.applicant.exception.badrequest.EvaluationStatusException;
import java.util.Arrays;
import java.util.function.Predicate;

public enum EvaluationStatus {

    ALL(card -> true),
    NOT_EVALUATED(ApplicantCard::hasEvaluation),
    EVALUATED(ApplicantCard::hasNoEvaluation);

    private final Predicate<ApplicantCard> predicate;

    EvaluationStatus(Predicate<ApplicantCard> predicate) {
        this.predicate = predicate;
    }

    public static boolean matchesEvaluationStatus(ApplicantCard card, String evaluationStatus) {
        return Arrays.stream(EvaluationStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(evaluationStatus))
                .findAny()
                .orElseThrow(EvaluationStatusException::new)
                .predicate
                .test(card);
    }
}
