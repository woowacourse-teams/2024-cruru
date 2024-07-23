package com.cruru.fixture;

import com.cruru.applicant.domain.Applicant;
import com.cruru.evaluation.domain.Evaluation;
import com.cruru.process.domain.Process;

public class EvaluationFixture {

    public static Evaluation createEvaluation() {
        return new Evaluation(5, "하드 스킬과 소프트 스킬이 출중함.", null, null);
    }

    public static Evaluation createEvaluation(Process process, Applicant applicant) {
        return new Evaluation(5, "하드 스킬과 소프트 스킬이 출중함.", process, applicant);
    }
}
