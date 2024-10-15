package com.cruru.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import com.cruru.auth.service.AuthService;
import com.cruru.club.domain.Club;
import com.cruru.club.domain.repository.ClubRepository;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.fixture.ClubFixture;
import com.cruru.util.fixture.LocalDateFixture;
import com.cruru.util.fixture.MemberFixture;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import jakarta.mail.internet.MimeMessage;
import java.time.Clock;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ControllerTest {

    private static final Clock FIXED_TIME = LocalDateFixture.fixedClock();

    protected RequestSpecification spec;
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
    @SpyBean
    private Clock clock;
    @MockBean
    private JavaMailSender javaMailSender;

    @BeforeEach
    void createDefaultLoginMember() {
        dbCleaner.truncateEveryTable();
        defaultMember = memberRepository.save(MemberFixture.ADMIN);
        defaultClub = clubRepository.save(ClubFixture.create(defaultMember));
        token = authService.createAccessToken(defaultMember).getToken();
    }

    @BeforeEach
    void setPort(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        spec = new RequestSpecBuilder()
                .addFilter(
                        documentationConfiguration(restDocumentation)
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint())
                ).build();
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
