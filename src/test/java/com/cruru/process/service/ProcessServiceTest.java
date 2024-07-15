package com.cruru.process.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.dashboard.exception.DashboardNotFoundException;
import com.cruru.process.controller.dto.ProcessesResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProcessServiceTest {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProcessService processService;

    @DisplayName("Id에 해당하는 대시보드의 프로세스 목록과 지원자 정보를 조회한다.")
    @Test
    void findByDashboardId() {
        // given
        Dashboard dashboard = new Dashboard("name", null);
        dashboard = dashboardRepository.save(dashboard);
        Process process = new Process(0, "name", "description", dashboard);
        process = processRepository.save(process);
        Applicant applicant = new Applicant("name", "email", "phone", process);
        applicant = applicantRepository.save(applicant);

        // when
        ProcessesResponse byDashboardId = processService.findByDashboardId(dashboard.getId());

        // then
        assertThat(byDashboardId.processResponses()).hasSize(1);
        assertThat(byDashboardId.processResponses().get(0).processId()).isEqualTo(process.getId());
        assertThat(byDashboardId.processResponses().get(0).dashboardApplicantDtos().get(0).applicantId()).isEqualTo(applicant.getId());
    }

    @DisplayName("대시보드가 존재하지 않는 경우, 예외가 발생한다.")
    @Test
    void invalidDashboardId() {
        // given
        long invalidId = 0;

        // when&then
        assertThatThrownBy(() -> processService.findByDashboardId(invalidId))
                .isInstanceOf(DashboardNotFoundException.class);
    }
}
