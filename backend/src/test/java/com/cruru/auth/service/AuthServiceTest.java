package com.cruru.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.cruru.auth.controller.response.TokenResponse;
import com.cruru.auth.domain.AccessToken;
import com.cruru.auth.domain.RefreshToken;
import com.cruru.auth.domain.Token;
import com.cruru.auth.domain.repository.RefreshTokenRepository;
import com.cruru.auth.exception.IllegalTokenException;
import com.cruru.member.domain.Member;
import com.cruru.member.domain.repository.MemberRepository;
import com.cruru.util.ServiceTest;
import com.cruru.util.fixture.MemberFixture;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AuthService 테스트")
class AuthServiceTest extends ServiceTest {

    private static final String TEST_SECRET_KEY = "test";
    private static final String EMAIL_CLAIM = "email";
    private static final String ROLE_CLAIM = "role";

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Member member;

    private Map<String, Object> claims;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(MemberFixture.DOBBY);
        claims = new HashMap<>();
        claims.put(EMAIL_CLAIM, "email@example.com");
        claims.put(ROLE_CLAIM, "ADMIN");
    }

    @DisplayName("AccessToken을 생성한다.")
    @Test
    void createAccessToken() {
        // given&when
        Token accessToken = authService.createAccessToken(member);

        // then
        assertAll(
                () -> assertThat(accessToken).isInstanceOf(AccessToken.class),
                () -> assertThat(accessToken.getToken()).isNotEmpty()
        );
    }

    @DisplayName("토큰이 유효한지 확인한다.")
    @Test
    void isTokenSignatureValid() {
        // given
        Token accessToken = authService.createAccessToken(member);

        // when
        boolean isValid = authService.isTokenSignatureValid(accessToken.getToken());

        // then
        assertThat(isValid).isTrue();
    }

    @DisplayName("토큰이 유효하지 않으면 false를 반환한다.")
    @Test
    void isTokenInvalid() {
        // given
        String invalidToken = "invalidToken";

        // when
        boolean isValid = authService.isTokenSignatureValid(invalidToken);

        // then
        assertThat(isValid).isFalse();
    }

    @DisplayName("토큰에서 이메일을 추출한다.")
    @Test
    void extractEmail() {
        // given
        Token accessToken = authService.createAccessToken(member);

        // when
        String email = authService.extractEmail(accessToken.getToken());

        // then
        assertThat(email).isEqualTo(member.getEmail());
    }

    @DisplayName("토큰에서 역할을 추출한다.")
    @Test
    void extractRole() {
        // given
        Token accessToken = authService.createAccessToken(member);

        // when
        String role = authService.extractMemberRole(accessToken.getToken());

        // then
        assertThat(role).isEqualTo(member.getRole().name());
    }

    @DisplayName("잘못된 토큰에서 이메일 추출 시 예외를 발생시킨다.")
    @Test
    void extractClaimThrowsIllegalTokenException() {
        // given
        String invalidToken = "invalidToken";

        // when & then
        assertThatThrownBy(() -> authService.extractEmail(invalidToken))
                .isInstanceOf(IllegalTokenException.class);
    }

    @DisplayName("비밀번호가 일치하지 않으면 true를 반환한다.")
    @Test
    void isNotVerifiedPassword() {
        // given
        String rawPassword = "plainPassword";
        String encodedPassword = "encodedPassword123";

        // when
        boolean result = authService.isNotVerifiedPassword(rawPassword, encodedPassword);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("Refresh Token 갱신에 성공한다.")
    @Test
    void refresh() throws InterruptedException {
        // given
        Token refreshToken = refreshTokenRepository.save((RefreshToken) authService.createRefreshToken(member));

        // when
        Thread.sleep(1000);
        TokenResponse tokenResponse = authService.refresh(refreshToken.getToken());

        // then
        assertThat(refreshToken.getToken()).isNotEqualTo(tokenResponse.refreshToken());
    }

    @DisplayName("토큰의 만료 여부를 검증한다.")
    @Test
    void isTokenExpired() {
        // given
        String expiredToken = generateExpiredToken();

        // when
        boolean expired = authService.isTokenExpired(expiredToken);

        // then
        assertThat(expired).isTrue();
    }

    @DisplayName("만료된 토큰의 클레임을 추출한다.")
    @Test
    void getEmail_TokenExpired() {
        // given
        String token = generateExpiredToken();

        // when
        String email = authService.extractEmail(token);
        String role = authService.extractMemberRole(token);

        // then
        String expectedRole = (String) claims.get(ROLE_CLAIM);
        String expectedEmail = (String) claims.get(EMAIL_CLAIM);

        assertAll(
                () -> assertThat(email).isEqualTo(expectedEmail),
                () -> assertThat(role).isEqualTo(expectedRole)
        );
    }

    private String generateExpiredToken() {
        Date now = new Date();
        Date validity = new Date(now.getTime() - 3600000); // 1시간 전 만료

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, TEST_SECRET_KEY)
                .compact();
    }
}
