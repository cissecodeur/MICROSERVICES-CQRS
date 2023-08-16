package com.sid.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private HttpStatus errorCode;

    public CustomException(String message, HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
