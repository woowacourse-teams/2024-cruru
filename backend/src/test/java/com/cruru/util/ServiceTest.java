package com.cruru.util;

import com.cruru.auth.controller.dto.LoginProfile;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
public class ServiceTest {

    @Autowired
    private DbCleaner dbCleaner;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    protected Member defaultMember;
    protected Club defaultClub;
    protected Dashboard defaultDashboard;
    protected LoginProfile loginProfile;


    @BeforeEach
    void resetDb() {
        dbCleaner.truncateEveryTable();
        defaultMember = memberRepository.save(MemberFixture.ADMIN);
        defaultClub = clubRepository.save(ClubFixture.create(defaultMember));
        defaultDashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        loginProfile = new LoginProfile(defaultMember.getEmail(), defaultMember.getRole());
    }
}
