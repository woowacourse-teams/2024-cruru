package com.cruru.applicant.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.applicant.controller.dto.ApplicantMoveRequest;
import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ApplicantServiceTest {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @DisplayName("여러 건의 지원서를 요청된 프로세스로 일괄 변경한다.")
    @Test
    void updateApplicantProcess() {
        // given
        Dashboard dashboard = dashboardRepository.save(new Dashboard(1L, "모집 공고1", null));
        Process beforeProcess = new Process(1L, 0, "이전 프로세스", "프로세스 설명1", dashboard);
        Process afterProcess = new Process(2L, 1, "이후 프로세스", "프로세스 설명2", dashboard);
        List<Process> processes = List.of(beforeProcess, afterProcess);
        processRepository.saveAll(processes);
        List<Applicant> applicants = List.of(new Applicant(1L, null, null, null, beforeProcess, false),
                new Applicant(2L, null, null, null, beforeProcess, false),
                new Applicant(3L, null, null, null, beforeProcess, false),
                new Applicant(4L, null, null, null, beforeProcess, false),
                new Applicant(5L, null, null, null, beforeProcess, false));
        applicantRepository.saveAll(applicants);

        // when
        List<Long> applicantIds = List.of(1L, 2L, 3L, 4L, 5L);
        ApplicantMoveRequest moveRequest = new ApplicantMoveRequest(applicantIds);
        applicantService.updateApplicantProcess(2L, moveRequest);

        // then
        List<Applicant> actualApplicants = applicantRepository.findAllById(applicantIds);
        boolean processAllMoved = actualApplicants.stream()
                .map(Applicant::getProcess)
                .allMatch(process -> process.equals(afterProcess));
        assertThat(processAllMoved).isTrue();
    }
}

