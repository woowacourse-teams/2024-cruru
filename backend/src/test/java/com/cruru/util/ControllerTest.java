package com.cruru.util;

import com.cruru.auth.service.AuthService;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.MemberFixture;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ControllerTest {

    protected Member defaultMember;
    protected Club defaultClub;
    protected String token;

    @LocalServerPort
    private int port;
    @Autowired
    private AuthService authService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private DbCleaner dbCleaner;

    @BeforeEach
    void createDefaultLoginMember() {
        dbCleaner.truncateEveryTable();
        defaultMember = memberRepository.save(MemberFixture.ADMIN);
        defaultClub = clubRepository.save(ClubFixture.create(defaultMember));
        token = authService.createToken(defaultMember);
    }

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }
}
