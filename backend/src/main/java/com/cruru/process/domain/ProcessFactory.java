package com.cruru.process.domain;

import com.cruru.dashboard.domain.Dashboard;
import java.util.ArrayList;
import java.util.List;

public class ProcessFactory {

    private static final int FIRST_SEQUENCE = 0;
    private static final String FIRST_PROCESS_NAME = "지원서 접수";
    private static final String FIRST_PROCESS_DESCRIPTION = "지원서를 제출하는 단계";
    private static final int LAST_SEQUENCE = 1;
    private static final String LAST_PROCESS_NAME = "최종 결과";
    private static final String LAST_PROCESS_DESCRIPTION = "최종 합격자 선별 단계";

    private ProcessFactory() {
        throw new IllegalStateException("유틸리티 클래스를 인스턴스를 생성할 수 없습니다.");
    }

    public static List<Process> createInitProcesses(Dashboard dashboard) {
        return new ArrayList<>(List.of(createFirstOf(dashboard), createLastOf(dashboard)));
    }

    private static Process createFirstOf(Dashboard dashboard) {
        return new Process(FIRST_SEQUENCE, FIRST_PROCESS_NAME, FIRST_PROCESS_DESCRIPTION, ProcessType.APPLY, dashboard);
    }

    private static Process createLastOf(Dashboard dashboard) {
        return new Process(LAST_SEQUENCE, LAST_PROCESS_NAME, LAST_PROCESS_DESCRIPTION, ProcessType.APPROVE, dashboard);
    }
}
