package com.cruru;

import com.cruru.applicant.domain.Applicant;
import com.cruru.applicant.domain.repository.ApplicantRepository;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.process.domain.Process;
import com.cruru.process.domain.repository.ProcessRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final DashboardRepository dashboardRepository;
    private final ProcessRepository processRepository;
    private final ApplicantRepository applicantRepository;

    @Value("${dataloader.enable}")
    private boolean enableDataLoader;

    @Override
    public void run(ApplicationArguments args) {
        if (enableDataLoader) {
            runDataLoader();
        }
    }

    private void runDataLoader() {
        Member member = new Member(1L, "member@mail.com", "qwer1234", "01012345678");
        memberRepository.save(member);
        Club club = new Club(1L, "크루루", member);
        clubRepository.save(club);
        Dashboard dashboard = new Dashboard(1L, "크루루 모집 공고", club);
        dashboardRepository.save(dashboard);

        Process firstProcess = new Process(1L, 0, "지원서", "지원서를 확인한다.", dashboard);
        Process lastProcess = new Process(2L, 0, "최종 합격", "최종 합격자", dashboard);
        processRepository.save(firstProcess);
        processRepository.save(lastProcess);

        List<Applicant> applicants = List.of(
                new Applicant(1L, "러기", "lurg@mail.com", "01011111111", firstProcess, false),
                new Applicant(2L, "도비", "doby@mail.com", "01022222222", firstProcess, false),
                new Applicant(3L, "아르", "arrr@mail.com", "01033333333", lastProcess, false),
                new Applicant(4L, "초코칩", "choc@mail.com", "01044444444", lastProcess, false),
                new Applicant(5L, "명오", "myun@mail.com", "01055555555", lastProcess, false),
                new Applicant(6L, "러시", "rush@mail.com", "01066666666", firstProcess, false),
                new Applicant(7L, "냥인", "nyan@mail.com", "01077777777", firstProcess, false),
                new Applicant(8L, "렛서", "pand@mail.com", "01088888888", firstProcess, false)
        );
        applicantRepository.saveAll(applicants);
    }
}
