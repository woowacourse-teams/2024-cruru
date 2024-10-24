package com.cruru.global;

import com.cruru.auth.controller.response.TokenResponse;
import com.cruru.auth.exception.IllegalCookieException;
import com.cruru.auth.exception.LoginUnauthorizedException;
import com.cruru.auth.service.AuthService;
import com.cruru.global.util.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseCookie;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String APPLYFORM_REQUEST_URI = "^/v1/applyform/\\d+$";

    private final AuthService authService;
    private final CookieManager cookieManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isGetApplyformRequest(request)) {
            return true;
        }

        if (isOptionsRequest(request) || isAuthenticated(request)) {
            return true;
        }

        if (isValidTokenExpired(request)) {
            refresh(request, response);
            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    private void refresh(HttpServletRequest request, HttpServletResponse response) {
        String token = cookieManager.extractRefreshToken(request);
        TokenResponse tokenResponse = authService.refresh(token);

        ResponseCookie accessTokenCookie = cookieManager.createAccessTokenCookie(tokenResponse.accessToken());
        ResponseCookie refreshTokenCookie = cookieManager.createRefreshTokenCookie(tokenResponse.refreshToken());

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    private boolean isGetApplyformRequest(HttpServletRequest request) {
        return request.getRequestURI().matches(APPLYFORM_REQUEST_URI) && isGetRequest(request);
    }

    private boolean isOptionsRequest(HttpServletRequest request) {
        return HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod());
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        try {
            String token = cookieManager.extractAccessToken(request);
            return authService.isTokenSignatureValid(token) && !authService.isTokenExpired(token);
        } catch (IllegalCookieException e) {
            throw new LoginUnauthorizedException();
        }
    }

    private boolean isValidTokenExpired(HttpServletRequest request) {
        try {
            String token = cookieManager.extractAccessToken(request);
            return authService.isTokenSignatureValid(token) && authService.isTokenExpired(token);
        } catch (IllegalCookieException e) {
            throw new LoginUnauthorizedException();
        }
    }

    private boolean isGetRequest(HttpServletRequest request) {
        return HttpMethod.GET.name().equalsIgnoreCase(request.getMethod());
    }
}
