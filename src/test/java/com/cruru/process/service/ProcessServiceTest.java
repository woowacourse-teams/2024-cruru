package com.cruru.process.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.dashboard.exception.DashboardNotFoundException;
import com.cruru.process.controller.dto.ProcessCreateRequest;
import com.cruru.process.controller.dto.ProcessesResponse;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("프로세스 서비스 테스트")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProcessServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    private DashboardRepository dashboardRepository;
    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private ProcessService processService;

    @DisplayName("ID에 해당하는 대시보드의 프로세스 목록과 지원자 정보를 조회한다.")
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

    @DisplayName("새로운 프로세스를 생성한다.")
    @Test
    void create() {
        // given
        Dashboard dashboard = new Dashboard("name", null);
        dashboard = dashboardRepository.save(dashboard);
        Process process1 = new Process(0, "name", "description", dashboard);
        process1 = processRepository.save(process1);
        Process process2 = new Process(1, "name", "description", dashboard);
        process2 = processRepository.save(process2);

        // when
        ProcessCreateRequest processCreateRequest = new ProcessCreateRequest("면접", "1차 면접", process1.getId());
        processService.create(dashboard.getId(), processCreateRequest);

        // then
        List<Process> allByDashboardId = processRepository.findAllByDashboardId(dashboard.getId())
                .stream()
                .sorted(Comparator.comparingInt(Process::getSequence))
                .toList();


        assertThat(allByDashboardId).hasSize(3);
        assertThat(allByDashboardId.get(1).getName()).isEqualTo("면접");
    }
}
