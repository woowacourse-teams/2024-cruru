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
public class QueryLoggingInterceptor implements HandlerInterceptor {

    private static final String QUERY_COUNT_LOG = "method={}, url={}, status={}, query_count={}, queries={}";
    private static final String QUERIES_FORMAT = "\"%s\"";
    private static final String QUERY_DELIMITER = "; ";

    private final QueryCounter queryCounter;

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        if (queryCounter.isWarn()) {
            log.warn(
                    QUERY_COUNT_LOG,
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    queryCounter.getCount(),
                    getFormattedQueries()
            );
        }
    }

    private String getFormattedQueries() {
        return String.format(
                QUERIES_FORMAT,
                String.join(QUERY_DELIMITER, queryCounter.getQueries())
        );
    }
}
