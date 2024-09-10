package com.cruru.advice;

import com.cruru.advice.badrequest.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private void logException(HttpServletRequest request, Exception exception, HttpStatus status) {
        // 스택 트레이스를 통해 예외가 발생한 클래스와 메서드 정보를 추출
        StackTraceElement[] stackTrace = exception.getStackTrace();
        StackTraceElement origin = stackTrace[0];

        String className = origin.getClassName();
        String methodName = origin.getMethodName();

        String httpRequestMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        String statusCode = String.valueOf(status.value());
        String exceptionName = exception.getClass().getSimpleName();
        String exceptionMessage = exception.getMessage();

        String logMessage = String.format(
                "\"httpRequestMethod\"=\"%s\", \"requestUri\"=\"%s\", \"statusCode\"=\"%s\", \"source-class\"=\"%s\", \"source-method\"=\"%s\", \"exception_class\"=\"%s\", \"exception_message\"=\"%s\", \"labels\"={ \"module\"=\"user-service\", \"environment\"=\"prod\" }",
                httpRequestMethod,
                requestUri,
                statusCode,
                className,
                methodName,
                exceptionName,
                exceptionMessage
        );
        log.warn(logMessage, exception);
    }

    private HttpServletRequest getCurrentHttpRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleBadRequestException(BadRequestException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        logException(request, e, HttpStatus.BAD_REQUEST);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleUnauthorizedException(UnauthorizedException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        logException(request, e, HttpStatus.UNAUTHORIZED);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleForbiddenException(ForbiddenException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        logException(request, e, HttpStatus.FORBIDDEN);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        logException(request, e, HttpStatus.NOT_FOUND);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        logException(request, e, HttpStatus.BAD_REQUEST);

        Map<String, String> validation = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            validation.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, validation.toString());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleConflictException(ConflictException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        logException(request, e, HttpStatus.CONFLICT);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleInternalServerException(InternalServerException e) {
        HttpServletRequest request = getCurrentHttpRequest();
        logException(request, e, HttpStatus.INTERNAL_SERVER_ERROR);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handleUnexpectedException(Exception e) {
        HttpServletRequest request = getCurrentHttpRequest();
        logException(request, e, HttpStatus.INTERNAL_SERVER_ERROR);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "예기치 못한 오류가 발생하였습니다."
        );
        return ResponseEntity.of(problemDetail).build();
    }
}
