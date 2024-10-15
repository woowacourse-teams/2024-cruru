package com.cruru.global.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.ProblemDetail;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MdcUtils {

    public static void setMdcForException(Exception e, ProblemDetail problemDetail) {
        setRequestId();
        setRequest();
        setStatusCode(problemDetail);
        setSource(e);
    }

    private static void setRequestId() {
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
    }

    private static void setRequest() {
        HttpServletRequest request = getCurrentHttpRequest();
        MDC.put("requestUri", request.getRequestURI());
        MDC.put("requestMethod", request.getMethod());
    }

    private static void setStatusCode(ProblemDetail problemDetail) {
        MDC.put("statusCode", String.valueOf(problemDetail.getStatus()));
    }

    private static void setSource(Exception e) {
        StackTraceElement source = e.getStackTrace()[0];
        MDC.put("sourceClass", source.getClassName());
        MDC.put("sourceMethod", source.getMethodName());
    }

    private static HttpServletRequest getCurrentHttpRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
