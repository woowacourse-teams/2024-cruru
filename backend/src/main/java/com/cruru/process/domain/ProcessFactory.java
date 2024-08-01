package com.cruru.process.domain;

import com.cruru.dashboard.domain.Dashboard;

public class ProcessFactory {

    private static final int FIRST_SEQUENCE = 1;
    private static final String FIRST_PROCESS_DEFAULT_NAME = "지원서 접수";
    private static final String FIRST_PROCESS_DEFAULT_DESCRIPTION = "지원서를 제출하는 단계";
    private static final int LAST_SEQUENCE = 1;
    private static final String LAST_PROCESS_DEFAULT_NAME = "최종 결과";
    private static final String LAST_PROCESS_DEFAULT_DESCRIPTION = "최종 합격자 선별 단계";

    private ProcessFactory() {
        throw new IllegalStateException("유틸리티 클래스를 인스턴스를 생성할 수 없습니다.");
    }

    public static Process createFirstOf(Dashboard dashboard) {
        return new Process(FIRST_SEQUENCE, FIRST_PROCESS_DEFAULT_NAME, FIRST_PROCESS_DEFAULT_DESCRIPTION, dashboard);
    }

    public static Process createLastOf(Dashboard dashboard) {
        return new Process(LAST_SEQUENCE, LAST_PROCESS_DEFAULT_NAME, LAST_PROCESS_DEFAULT_DESCRIPTION, dashboard);
    }
}
