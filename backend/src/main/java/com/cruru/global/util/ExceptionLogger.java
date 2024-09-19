package com.cruru.global.util;

import com.cruru.advice.CruruCustomException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionLogger {

    public static void info(HttpServletRequest request, CruruCustomException exception) {
        setMDC(request, exception);
        log.info("handle info level exception");
        clearMDC();
    }

    // MDC에 메타데이터 설정
    private static void setMDC(HttpServletRequest request, CruruCustomException exception) {
        StackTraceElement origin = exception.getStackTrace()[0];

        MDC.put("httpMethod", request.getMethod());
        MDC.put("requestUri", request.getRequestURI());
        MDC.put("statusCode", exception.getStatusCode());
        MDC.put("sourceClass", origin.getClassName());
        MDC.put("sourceMethod", origin.getMethodName());
        MDC.put("exceptionClass", exception.getClass().getSimpleName());
        MDC.put("exceptionMessage", exception.getMessage());
    }

    // MDC 초기화
    public static void info(ProblemDetail problemDetail) {
        setMDC(problemDetail);
        log.info("handle info level exception");
        clearMDC();
    }

    public static void warn(ProblemDetail problemDetail) {
        setMDC(problemDetail);
        log.warn("handle warn level exception");
        clearMDC();
    }

    public static void error(ProblemDetail problemDetail) {
        setMDC(problemDetail);
        log.error("handle error level exception");
        clearMDC();
    }

    private static void setMDC(ProblemDetail problemDetail) {
        Map<String, Object> details = problemDetail.getProperties();
        Map<String, String> map = new HashMap<>();
        for (Entry<String, Object> stringObjectEntry : details.entrySet()) {
            map.put(stringObjectEntry.getKey(), java.lang.String.valueOf(stringObjectEntry.getValue()));
        }
        MDC.setContextMap(map);
    }

    private static void clearMDC() {
        MDC.clear();
    }
}
