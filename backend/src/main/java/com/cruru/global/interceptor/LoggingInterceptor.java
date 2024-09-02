package com.cruru.global.interceptor;

import com.cruru.aspect.query.QueryCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private static final String QUERY_COUNT_LOG = "METHOD: {}, URL: {}, STATUS_CODE: {}, QUERY_COUNT: {}";

    private final QueryCounter queryCounter;

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        if (queryCounter.isWarn()) {
            log.warn(QUERY_COUNT_LOG, request.getMethod(), request.getRequestURI(), response.getStatus(),
                    queryCounter.getCount());
        }
    }
}
