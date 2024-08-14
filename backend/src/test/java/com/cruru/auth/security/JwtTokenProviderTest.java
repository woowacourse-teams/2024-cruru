package com.cruru.auth.security;

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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Member member;
    private String token;

    @BeforeEach
    void setUp() {
        member = MemberFixture.createMember1();
        token = jwtTokenProvider.create(member);
    }

    @Test
    @DisplayName("토큰이 정상적으로 생성되는지 확인한다")
    void createToken() {
        // given&when
        Claims claims = Jwts.parser().setSigningKey("test".getBytes()).parseClaimsJws(token).getBody();

        // then
        String expectedRole = member.getRole().name();
        String expectedEmail = member.getEmail();
        assertAll(() -> {
            assertThat(claims).containsEntry("role", expectedRole);
            assertThat(claims).containsEntry("email", expectedEmail);
            assertThat(claims.getExpiration()).isNotNull();
        });
    }

    @DisplayName("유효한 토큰에서 이메일과 역할을 추출할 수 있는지 확인한다")
    @Test
    void testExtractEmailAndRole() {
        // given&when
        String email = jwtTokenProvider.extractMemberEmail(token);
        String role = jwtTokenProvider.extractMemberRole(token);

        // then
        String expectedRole = member.getRole().name();
        String expectedEmail = member.getEmail();
        assertThat(email).isEqualTo(expectedEmail);
        assertThat(role).isEqualTo(expectedRole);
    }

    @DisplayName("만료된 토큰인지 검증한다.")
    @Test
    void expiredTokenThrowsException() {
        // given
        String expiredToken = generateExpiredToken();

        // when&then
        assertThat(jwtTokenProvider.isExpired(expiredToken)).isTrue();
    }

    private String generateExpiredToken() {
        Date now = new Date();
        Date validity = new Date(now.getTime() - 3600000);

        return Jwts.builder()
                .claim("role", "CLUB_OWNER")
                .claim("email", "test@example.com")
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, "test".getBytes())
                .compact();
    }
}
