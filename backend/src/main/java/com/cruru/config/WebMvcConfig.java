package com.cruru.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

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
}
