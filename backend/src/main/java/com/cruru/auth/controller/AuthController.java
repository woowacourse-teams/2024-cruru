package com.cruru.auth.controller;

import com.cruru.auth.controller.request.LoginRequest;
import com.cruru.auth.controller.response.LoginResponse;
import com.cruru.auth.controller.response.TokenResponse;
import com.cruru.auth.facade.AuthFacade;
import com.cruru.club.facade.ClubFacade;
import com.cruru.global.util.CookieManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;
    private final ClubFacade clubFacade;
    private final CookieManager cookieManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        TokenResponse tokenResponse = authFacade.login(request);
        long clubId = clubFacade.findByMemberEmail(request.email());
        ResponseCookie accessTokenCookie = cookieManager.createAccessTokenCookie(tokenResponse.accessToken());
        ResponseCookie refreshTokenCookie = cookieManager.createRefreshTokenCookie(tokenResponse.refreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(new LoginResponse(clubId));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie accessTokenCookie = cookieManager.clearAccessTokenCookie();
        ResponseCookie refreshTokenCookie = cookieManager.clearRefreshTokenCookie();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }
}
