package com.cruru.global.util;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionLogger {

    public static void info(HttpServletRequest request, Exception exception, HttpStatus status) {
        String logMessage = buildLog(request, exception, status);
        log.info(logMessage);
    }

    public static void warn(HttpServletRequest request, Exception exception, HttpStatus status) {
        String logMessage = buildLog(request, exception, status);
        log.warn(logMessage);
    }

    public static void error(HttpServletRequest request, Exception exception, HttpStatus status) {
        String logMessage = buildLog(request, exception, status);
        log.error(logMessage, exception);
    }

    private static String buildLog(HttpServletRequest request, Exception exception, HttpStatus status) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        StackTraceElement origin = stackTrace[0];

        String className = origin.getClassName();
        String methodName = origin.getMethodName();

        String httpRequestMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        String statusCode = String.valueOf(status.value());
        String exceptionName = exception.getClass().getSimpleName();
        String exceptionMessage = exception.getMessage();

        return String.format(
                "\"httpRequestMethod\"=\"%s\", \"requestUri\"=\"%s\", \"statusCode\"=\"%s\", \"source-class\"=\"%s\", \"source-method\"=\"%s\", \"exception_class\"=\"%s\", \"exception_message\"=\"%s\", \"labels\"={ \"module\"=\"user-service\", \"environment\"=\"prod\" }",
                httpRequestMethod,
                requestUri,
                statusCode,
                className,
                methodName,
                exceptionName,
                exceptionMessage
        );
    }
}
