package com.kancth.nomad.domain.user.exception;

import com.kancth.nomad.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends BaseException {
    public EmailAlreadyExistsException() {
        super("error.user.emailAlreadyExists", HttpStatus.CONFLICT);
    }
}
