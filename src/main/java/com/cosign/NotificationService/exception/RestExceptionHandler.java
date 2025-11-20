package com.cosign.NotificationService.exception;

import com.cosign.NotificationService.common.ApiResponse;
import com.cosign.NotificationService.constants.ApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ConditionalOnWebApplication
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Binding failed");
        body.put("details", ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(e.getErrorCode())
                        .message(e.getErrorMsg())
                        .build());
    }

    @ExceptionHandler(InfraException.class)
    public ResponseEntity<ApiResponse<Object>> handleInfraException(InfraException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(ApiStatus.BAD_REQUEST.getMessage())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleOtherException(Exception e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(ApiStatus.BAD_REQUEST.getMessage())
                        .message(e.getMessage())
                        .build());
    }
}

