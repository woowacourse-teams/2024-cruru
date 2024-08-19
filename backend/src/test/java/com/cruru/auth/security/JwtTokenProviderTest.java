package com.cruru.auth.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.auth.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
    private static final String EMAIL_CLAIM = "email";
    private static final String ROLE_CLAIM = "role";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Map<String, Object> claims;

    @BeforeEach
    void setUp() {
        claims = new HashMap<>();
        claims.put(EMAIL_CLAIM, "email@example.com");
        claims.put(ROLE_CLAIM, "ADMIN");
    }

    @DisplayName("토큰이 정상적으로 생성되는지 확인한다")
    @Test
    void create() {
        // given&when
        String token = jwtTokenProvider.createToken(claims);
        Claims extractedClaims = Jwts.parser()
                .setSigningKey(TEST_SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();

        // then
        String expectedRole = (String) claims.get(ROLE_CLAIM);
        String expectedEmail = (String) claims.get(EMAIL_CLAIM);

        assertAll(
                () -> assertThat(extractedClaims).containsEntry(ROLE_CLAIM, expectedRole),
                () -> assertThat(extractedClaims).containsEntry(EMAIL_CLAIM, expectedEmail),
                () -> assertThat(extractedClaims.getExpiration()).isNotNull()
        );
    }

    @DisplayName("유효한 토큰에서 이메일과 역할을 추출할 수 있는지 확인한다")
    @Test
    void extractEmailAndRole() {
        // given
        String token = jwtTokenProvider.createToken(claims);

        // when
        String email = jwtTokenProvider.extractClaim(token, EMAIL_CLAIM);
        String role = jwtTokenProvider.extractClaim(token, ROLE_CLAIM);

        // then
        String expectedRole = (String) claims.get(ROLE_CLAIM);
        String expectedEmail = (String) claims.get(EMAIL_CLAIM);

        assertAll(
                () -> assertThat(email).isEqualTo(expectedEmail),
                () -> assertThat(role).isEqualTo(expectedRole)
        );
    }

    @DisplayName("만료된 토큰을 검증한다.")
    @Test
    void isAlive() {
        // given
        String expiredToken = generateExpiredToken();

        // when&then
        assertThat(jwtTokenProvider.isAlive(expiredToken)).isFalse();
    }

    private String generateExpiredToken() {
        Date now = new Date();
        Date validity = new Date(now.getTime() - 3600000); // 1시간 전 만료

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, TEST_SECRET_KEY.getBytes())
                .compact();
    }

    @DisplayName("만료되지 않은 토큰을 검증한다.")
    @Test
    void isAlive_notValid() {
        // given
        String notExpiredToken = jwtTokenProvider.createToken(claims);

        // when&then
        assertThat(jwtTokenProvider.isAlive(notExpiredToken)).isTrue();
    }
}
