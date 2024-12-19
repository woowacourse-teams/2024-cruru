package com.cruru.util.fixture;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.Evaluation;
import com.cruru.process.domain.Process;

public class EvaluationFixture {

    public static Evaluation fivePoints() {
        return new Evaluation("김도엽", 5, "서류가 인상 깊었습니다.", null, null);
    }

    public static Evaluation fivePoints(Process process, Applicant applicant) {
        return new Evaluation("김도엽", 5, "서류가 인상 깊었습니다.", process, applicant);
    }

    public static Evaluation fourPoints() {
        return new Evaluation("김도엽", 4, "포트폴리오가 인상 깊었습니다.", null, null);
    }

    public static Evaluation fourPoints(Process process, Applicant applicant) {
        return new Evaluation("김도엽", 4, "포트폴리오가 인상 깊었습니다.", process, applicant);
    }
}
