package com.kancth.nomad.global.exception;

import com.kancth.nomad.global.converter.ExceptionMessageConverter;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {
    private final String message;
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseException() {
        this.message = ExceptionMessageConverter.getMessage("error.common.unknownError");
    }

    public BaseException(String errorCode) {
        this.message = ExceptionMessageConverter.getMessage(errorCode);
    }

    public BaseException(String errorCode, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = ExceptionMessageConverter.getMessage(errorCode);
    }
}