package com.kancth.nomad.domain.security.exception;

import com.kancth.nomad.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super("error.jwt.invalidToken", HttpStatus.UNAUTHORIZED);
    }
}
