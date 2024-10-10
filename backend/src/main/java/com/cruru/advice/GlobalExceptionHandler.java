package com.cruru.advice;

import com.cruru.global.util.MdcUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final int STACK_TRACE_LIMIT = 5;

    @ExceptionHandler(CruruCustomException.class)
    public ResponseEntity<Object> handleCustomException(CruruCustomException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());

        return handleExceptionWithMdc(e, problemDetail,
                ex -> log.info("CRURU_EXCEPTION [errorMessage = {}]", ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류입니다.");

        return handleExceptionWithMdc(e, problemDetail,
                ex -> log.error("SERVER_EXCEPTION [errorMessage = {}, stackTrace = {}]",
                        ex.getMessage(),
                        getLimitedStackTrace(ex))
        );
    }

    private String getLimitedStackTrace(Exception e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();

        return Arrays.stream(stackTraceElements)
                .limit(STACK_TRACE_LIMIT)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, String> validation = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            validation.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, validation.toString());

        return handleExceptionWithMdc(e, problemDetail,
                ex -> log.warn("METHOD_ARGUMENT_EXCEPTION [errorMessage = {}]", validation)
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception e,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(statusCode, "오류가 발생했습니다. 다시 시도해 주세요.");

        return handleExceptionWithMdc(e, problemDetail,
                ex -> log.warn("SPRING_BASIC_EXCEPTION [errorMessage = {}]", ex.getMessage())
        );
    }

    private ResponseEntity<Object> handleExceptionWithMdc(
            Exception e,
            ProblemDetail problemDetail,
            ExceptionCallback callback
    ) {
        MdcUtils.setMdcForException(e, problemDetail);
        callback.handleException(e);
        MDC.clear();
        return ResponseEntity.of(problemDetail).build();
    }
}
