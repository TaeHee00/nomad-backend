package com.kancth.nomad.domain.auth.exception;

import com.kancth.nomad.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BaseException {
    public InvalidPasswordException() {
        super("error.auth.invalidPassword", HttpStatus.UNAUTHORIZED);
    }
}
