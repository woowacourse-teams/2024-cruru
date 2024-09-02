package com.cruru.config;

import com.cruru.auth.service.AuthService;
import com.cruru.global.interceptor.AuthenticationInterceptor;
import com.cruru.global.interceptor.QueryLoggingInterceptor;
import com.cruru.global.resolver.LoginArgumentResolver;
import com.cruru.global.util.CookieManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final CookieManager cookieManager;
    private final QueryLoggingInterceptor queryLoggingInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("https://*.cruru.kr")
                .allowedOrigins(
                        "http://localhost:3000",
                        "https://localhost:3000",
                        "https://cruru.kr",
                        "https://dbrc7l2k8z4lk.cloudfront.net",
                        "https://d22au4hc21uq4d.cloudfront.net"
                )
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(authService, cookieManager))
                .addPathPatterns("/**")
                .excludePathPatterns("/**/signup")
                .excludePathPatterns("/**/login")
                .excludePathPatterns("/**/applyform/**")
                .excludePathPatterns("/**/posts/**")
        ;

        registry.addInterceptor(queryLoggingInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver(authService, cookieManager));
    }
}
