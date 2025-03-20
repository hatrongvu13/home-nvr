package com.xxx.exception;

import com.xxx.data.response.ApiResponse;
import com.xxx.factory.ApiFactory;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ApiFactory apiResFactory;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return apiResFactory.failWithBadInputParameter(ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handlerConstraintViolationException(ConstraintViolationException ex) {
        return apiResFactory.failWithBadInputParameter(ex);
    }

    @ExceptionHandler(XxxException.class)
    public ResponseEntity<ApiResponse> handlerXxxException(XxxException exception) {
        return apiResFactory.failWithDomainException(exception);
    }
}
