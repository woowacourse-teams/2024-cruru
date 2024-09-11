package com.cruru.advice;

import com.cruru.advice.badrequest.BadRequestException;
import com.cruru.global.util.ExceptionLogger;
import jakarta.servlet.http.HttpServletRequest;
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
        ExceptionLogger.info(request, e, HttpStatus.BAD_REQUEST);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    private HttpServletRequest getCurrentHttpRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleUnauthorizedException(UnauthorizedException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e, HttpStatus.UNAUTHORIZED);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleForbiddenException(ForbiddenException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e, HttpStatus.FORBIDDEN);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e, HttpStatus.NOT_FOUND);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleConflictException(ConflictException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e, HttpStatus.CONFLICT);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleInternalServerException(InternalServerException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        ExceptionLogger.info(request, e, HttpStatus.INTERNAL_SERVER_ERROR);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception e, WebRequest request) {
        HttpServletRequest httpRequest = getCurrentHttpRequest();
        ProblemDetail problemDetail = handleException(e, request);
        HttpStatus statusCode = HttpStatus.valueOf(problemDetail.getStatus());
        if (statusCode.is5xxServerError()) {
            ExceptionLogger.error(httpRequest, e, statusCode);
        } else {
            ExceptionLogger.warn(httpRequest, e, statusCode);
        }
        return ResponseEntity.of(problemDetail).build();
    }

    private ProblemDetail handleException(Exception e, WebRequest request) {
        try {
            return (ProblemDetail) Objects.requireNonNull(handler.handleException(e, request)).getBody();
        } catch (Exception ex) {
            return ProblemDetail.forStatusAndDetail(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "예기치 못한 오류가 발생하였습니다."
            );
        }
    }
}
