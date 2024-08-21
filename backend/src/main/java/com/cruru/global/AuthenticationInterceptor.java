package com.cruru.global;

import com.cruru.auth.exception.IllegalCookieException;
import com.cruru.auth.exception.LoginUnauthorizedException;
import com.cruru.auth.service.AuthService;
import com.cruru.global.util.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private final CookieManager cookieManager;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        if (isOptionsRequest(request) || isAuthenticated(request)) {
            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    private boolean isOptionsRequest(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        try {
            String token = cookieManager.extractToken(request);
            return authService.isTokenValid(token);
        } catch (IllegalCookieException e) {
            throw new LoginUnauthorizedException();
        }
    }
}
