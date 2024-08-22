package com.cruru.global;

import com.cruru.advice.UnauthorizedException;
import com.cruru.auth.controller.dto.LoginProfile;
import com.cruru.auth.service.AuthService;
import com.cruru.global.util.CookieManager;
import com.cruru.member.domain.MemberRole;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;
    private final CookieManager cookieManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType()
                .equals(LoginProfile.class);
    }

    @Override
    public LoginProfile resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws UnauthorizedException {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String token = cookieManager.extractToken(request);
        String emailPayload = authService.extractEmail(token);
        MemberRole memberRolePayload = MemberRole.valueOf(authService.extractMemberRole(token));

        return new LoginProfile(emailPayload, memberRolePayload);
    }
}
