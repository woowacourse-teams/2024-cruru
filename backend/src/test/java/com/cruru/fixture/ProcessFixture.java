package com.cruru.fixture;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;

public class ProcessFixture {

    public static Process createFirstProcess() {
        return new Process(0, "지원 접수", "지원자가 지원서를 제출하는 단계", null);
    }

    public static Process createFirstProcess(Dashboard dashboard) {
        return new Process(0, "지원 접수", "지원자가 지원서를 제출하는 단계", dashboard);
    }

    public static Process createFinalProcess() {
        return new Process(1, "최종 합격", "지원자가 최종적으로 합격한 단계", null);
    }

    public static Process createFinalProcess(Dashboard dashboard) {
        return new Process(1, "최종 합격", "지원자가 최종적으로 합격한 단계", dashboard);
    }

    public static Process createInterviewProcess(Dashboard dashboard) {
        return new Process(1, "1차 면접", "화상 면접", dashboard);
    }
}
