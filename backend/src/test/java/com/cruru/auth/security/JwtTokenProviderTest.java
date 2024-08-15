package com.cruru.auth.security;

import static com.cruru.member.domain.MemberRole.CLUB_OWNER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.member.domain.Member;
import com.cruru.util.fixture.MemberFixture;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("JWT Token Provider 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
class JwtTokenProviderTest {

    private static final String TEST_SECRET_KEY = "test";
    private static final String PAYLOAD_KEY_ROLE = "role";
    private static final String PAYLOAD_KEY_EMAIL = "email";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Member member;

    @BeforeEach
    void setUp() {
        member = MemberFixture.createMember1();
    }

    @DisplayName("토큰이 정상적으로 생성되는지 확인한다")
    @Test
    void create() {
        // when
        String token = jwtTokenProvider.createToken(member);
        Claims claims = Jwts.parser()
                .setSigningKey(TEST_SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();

        // then
        String expectedRole = member.getRole().name();
        String expectedEmail = member.getEmail();
        assertAll(() -> {
            assertThat(claims).containsEntry(PAYLOAD_KEY_ROLE, expectedRole);
            assertThat(claims).containsEntry(PAYLOAD_KEY_EMAIL, expectedEmail);
            assertThat(claims.getExpiration()).isNotNull();
        });
    }

    @DisplayName("유효한 토큰에서 이메일과 역할을 추출할 수 있는지 확인한다")
    @Test
    void extractEmailAndRole() {
        // given
        String token = jwtTokenProvider.createToken(member);

        // when
        String email = jwtTokenProvider.extractPayload(token, "email");
        String role = jwtTokenProvider.extractPayload(token, "role");

        // then
        String expectedRole = member.getRole().name();
        String expectedEmail = member.getEmail();
        assertThat(email).isEqualTo(expectedEmail);
        assertThat(role).isEqualTo(expectedRole);
    }

    @DisplayName("만료된 토큰을 검증한다.")
    @Test
    void isExpired_expired() {
        // given
        String expiredToken = generateExpiredToken();

        // when&then
        assertThat(jwtTokenProvider.isExpired(expiredToken)).isTrue();
    }

    private String generateExpiredToken() {
        Date now = new Date();
        Date validity = new Date(now.getTime() - 3600000); // 1시간 전 만료

        return Jwts.builder()
                .claim(PAYLOAD_KEY_ROLE, CLUB_OWNER.name())
                .claim(PAYLOAD_KEY_EMAIL, "test@example.com")
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, TEST_SECRET_KEY.getBytes())
                .compact();
    }

    @DisplayName("만료되지 않은 토큰을 검증한다.")
    @Test
    void isExpired_notExpired() {
        // given
        String notExpiredToken = jwtTokenProvider.createToken(member);

        // when&then
        assertThat(jwtTokenProvider.isExpired(notExpiredToken)).isFalse();
    }
}
