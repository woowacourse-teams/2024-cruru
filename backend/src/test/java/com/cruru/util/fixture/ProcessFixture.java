package com.cruru.util.fixture;

import com.cruru.dashboard.domain.Dashboard;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.ProcessType;
import java.util.List;

public class ProcessFixture {

    public static List<Process> maxSizeOf(Dashboard dashboard) {
        return List.of(
                createFirstProcess(dashboard),
                new Process(1, "코딩 테스트", "온라인", ProcessType.EVALUATE, dashboard),
                new Process(2, "CS 테스트", "온라인", ProcessType.EVALUATE, dashboard),
                new Process(3, "1차 면접", "화상 면접", ProcessType.EVALUATE, dashboard),
                createFinalProcess(dashboard)
        );
    }

    public static Process createFirstProcess(Dashboard dashboard) {
        return new Process(0, "지원 접수", "지원자가 지원서를 제출하는 단계", ProcessType.APPLY, dashboard);
    }

    public static Process createFinalProcess(Dashboard dashboard) {
        return new Process(1, "최종 합격", "지원자가 최종적으로 합격한 단계", ProcessType.APPROVE, dashboard);
    }

    public static Process createFirstProcess() {
        return new Process(0, "지원 접수", "지원자가 지원서를 제출하는 단계", ProcessType.APPLY, null);
    }

    public static Process createFinalProcess() {
        return new Process(1, "최종 합격", "지원자가 최종적으로 합격한 단계", ProcessType.APPROVE, null);
    }

    public static Process createInterviewProcess(Dashboard dashboard) {
        return new Process(1, "1차 면접", "화상 면접", ProcessType.EVALUATE, dashboard);
    }
}
