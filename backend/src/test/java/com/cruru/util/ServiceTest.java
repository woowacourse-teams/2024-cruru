package com.cruru.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.dashboard.domain.Dashboard;
import com.cruru.dashboard.domain.repository.DashboardRepository;
import com.cruru.global.LoginProfile;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.DashboardFixture;
import com.cruru.util.fixture.LocalDateFixture;
import com.cruru.util.fixture.MemberFixture;
import jakarta.mail.internet.MimeMessage;
import java.time.Clock;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
public class ServiceTest {

    private static final Clock FIXED_TIME = LocalDateFixture.fixedClock();

    protected Member defaultMember;
    protected Club defaultClub;
    protected Dashboard defaultDashboard;
    protected LoginProfile loginProfile;
    @MockBean
    protected JavaMailSender javaMailSender;
    @Autowired
    private DbCleaner dbCleaner;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private DashboardRepository dashboardRepository;
    @SpyBean
    private Clock clock;

    @BeforeEach
    void resetDb() {
        dbCleaner.truncateEveryTable();
        defaultMember = memberRepository.save(MemberFixture.ADMIN);
        defaultClub = clubRepository.save(ClubFixture.create(defaultMember));
        defaultDashboard = dashboardRepository.save(DashboardFixture.backend(defaultClub));
        loginProfile = new LoginProfile(defaultMember.getEmail(), defaultMember.getRole());
    }

    @BeforeEach
    void setClock() {
        doReturn(Instant.now(FIXED_TIME))
                .when(clock)
                .instant();
    }

    @BeforeEach
    void setJavaMailSender() {
        doReturn(mock(MimeMessage.class))
                .when(javaMailSender).createMimeMessage();
        doNothing()
                .when(javaMailSender).send(any(MimeMessage.class));
    }
}
