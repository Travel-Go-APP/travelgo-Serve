package com.travelgo.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus getStatus();
    String getErrorCode();
    String getMessage();
}
