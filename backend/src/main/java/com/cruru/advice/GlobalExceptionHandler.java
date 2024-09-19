package com.cruru.advice;

import com.cruru.advice.badrequest.BadRequestException;
import com.cruru.global.util.ExceptionLogger;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final UncatchedExceptionHandler handler;

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleBadRequestException(BadRequestException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleUnauthorizedException(UnauthorizedException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleForbiddenException(ForbiddenException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleConflictException(ConflictException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleInternalServerException(InternalServerException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );
        return ResponseEntity.of(problemDetail).build();
    }

    private HttpServletRequest getCurrentHttpRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception e, WebRequest request) {
        ProblemDetail problemDetail = handleException(e, request);
        HttpStatus statusCode = HttpStatus.valueOf(problemDetail.getStatus());
        if (statusCode.is5xxServerError()) {
            ExceptionLogger.error(problemDetail);
        } else {
            ExceptionLogger.warn(problemDetail);
        }
        ProblemDetail detailsToSend = ProblemDetail.forStatusAndDetail(statusCode, problemDetail.getDetail());
        return ResponseEntity.of(detailsToSend).build();
    }

    private ProblemDetail handleException(Exception e, WebRequest request) {
        try {
            ProblemDetail problemDetail = (ProblemDetail) Objects.requireNonNull(handler.handleException(e, request))
                    .getBody();
            problemDetail.setProperties(setDetails(getCurrentHttpRequest(), e, HttpStatus.OK));
            return problemDetail;
        } catch (Exception ex) {
            return ProblemDetail.forStatusAndDetail(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "예기치 못한 오류가 발생하였습니다."
            );
        }
    }

    private static Map<String, Object> setDetails(HttpServletRequest request, Exception exception, HttpStatus status) {
        StackTraceElement origin = exception.getStackTrace()[0];
        Map<String, Object> map = new HashMap<>();
        map.put("httpMethod", request.getMethod());
        map.put("requestUri", request.getRequestURI());
        map.put("statusCode", String.valueOf(status.value()));
        map.put("sourceClass", origin.getClassName());
        map.put("sourceMethod", origin.getMethodName());
        map.put("exceptionClass", exception.getClass().getSimpleName());
        map.put("exceptionMessage", exception.getMessage());
        return map;
    }
}
