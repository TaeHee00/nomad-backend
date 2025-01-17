package com.kancth.nomad.domain.security.exception;

import com.kancth.nomad.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException() {
        super("error.jwt.expiredToken", HttpStatus.UNAUTHORIZED);
    }
}
