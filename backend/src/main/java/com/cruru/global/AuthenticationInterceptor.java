package com.cruru.global;

import com.cruru.auth.controller.response.TokenResponse;
import com.cruru.auth.exception.IllegalCookieException;
import com.cruru.auth.exception.LoginUnauthorizedException;
import com.cruru.auth.service.AuthService;
import com.cruru.global.util.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String APPLYFORM_REQUEST_URI = "^/v1/applyform/\\d+$";

    private final AuthService authService;
    private final CookieManager cookieManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("Request URI: {}, Method: {}", request.getRequestURI(), request.getMethod());  // 로그 추가

        if (isGetApplyformRequest(request)) {
            log.info("GET applyform request is allowed");
            return true;
        }

        if (isOptionsRequest(request) || isAuthenticated(request)) {
            log.info("OPTIONS request or valid authentication");
            return true;
        }

        if (isValidTokenExpired(request)) {
            log.info("Token expired, attempting refresh...");
            refresh(request, response);
            return true;
        }

        log.warn("Unauthorized request: {}", request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    private void refresh(HttpServletRequest request, HttpServletResponse response) {
        String token = cookieManager.extractRefreshToken(request);
        log.info("Refresh token extracted: {}", token);

        TokenResponse tokenResponse = authService.refresh(token);

        ResponseCookie accessTokenCookie = cookieManager.createAccessTokenCookie(tokenResponse.accessToken());
        ResponseCookie refreshTokenCookie = cookieManager.createRefreshTokenCookie(tokenResponse.refreshToken());

        log.info("New access and refresh tokens created");

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    private boolean isGetApplyformRequest(HttpServletRequest request) {
        boolean result = request.getRequestURI().matches(APPLYFORM_REQUEST_URI) && isGetRequest(request);
        log.info("Is GET applyform request: {}", result);
        return result;
    }

    private boolean isOptionsRequest(HttpServletRequest request) {
        boolean result = HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod());
        log.info("Is OPTIONS request: {}", result);
        return result;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        try {
            String token = cookieManager.extractAccessToken(request);
            log.info("Access token extracted: {}", token);
            boolean valid = authService.isTokenSignatureValid(token) && !authService.isTokenExpired(token);
            log.info("Is authenticated: {}", valid);
            return valid;
        } catch (IllegalCookieException e) {
            log.error("Illegal cookie exception", e);
            throw new LoginUnauthorizedException();
        }
    }

    private boolean isValidTokenExpired(HttpServletRequest request) {
        try {
            String token = cookieManager.extractAccessToken(request);
            log.info("Access token extracted: {}", token);
            boolean expired = authService.isTokenSignatureValid(token) && authService.isTokenExpired(token);
            log.info("Is token expired: {}", expired);
            return expired;
        } catch (IllegalCookieException e) {
            log.error("Illegal cookie exception", e);
            throw new LoginUnauthorizedException();
        }
    }

    private boolean isGetRequest(HttpServletRequest request) {
        boolean result = HttpMethod.GET.name().equalsIgnoreCase(request.getMethod());
        log.info("Is GET request: {}", result);
        return result;
    }
}
