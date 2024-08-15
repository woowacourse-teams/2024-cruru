package com.cruru.global;

import com.cruru.auth.service.AuthService;
import com.cruru.global.util.CookieManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        String token = CookieManager.extractToken(request);

        if (authService.isLoginMember(token)) {
            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
