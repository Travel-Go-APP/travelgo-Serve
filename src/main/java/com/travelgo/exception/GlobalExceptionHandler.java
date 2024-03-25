package com.travelgo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TravelGoException.class)
    public ResponseEntity<ErrorResponse> bravoHealthParkException(TravelGoException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        LOGGER.error("HandleException call in advice, {}", exception.getErrorCode());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.from(errorCode));
    }
}

