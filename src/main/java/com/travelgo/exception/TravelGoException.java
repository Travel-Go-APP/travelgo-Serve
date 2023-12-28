package com.travelgo.exception;


import lombok.Getter;

@Getter
public class TravelGoException extends RuntimeException{
    private final ErrorCode errorCode;

    public TravelGoException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static TravelGoException type(ErrorCode errorCode) {
        return new TravelGoException(errorCode);
    }
}
