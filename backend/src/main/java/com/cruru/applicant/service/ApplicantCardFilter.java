package com.cruru.applicant.service;

import com.cruru.applicant.domain.EvaluationStatus;
import com.cruru.applicant.domain.dto.ApplicantCard;

public class ApplicantCardFilter {

    private ApplicantCardFilter() {}

    public static boolean filterByScore(ApplicantCard card, Double minScore, Double maxScore) {
        double avgScore = card.averageScore();
        return minScore <= avgScore && avgScore <= maxScore;
    }

    public static boolean filterByEvaluationStatus(ApplicantCard card, EvaluationStatus evaluationExists) {
        long evaluationCount = card.evaluationCount();
        if (evaluationExists == EvaluationStatus.ALL) {
            return true;
        }
        if (evaluationExists == EvaluationStatus.NO_EVALUATED) {
            return evaluationCount == 0;
        }
        if (evaluationExists == EvaluationStatus.EVALUATED) {
            return evaluationCount > 0;
        }
        return false;
    }
}

