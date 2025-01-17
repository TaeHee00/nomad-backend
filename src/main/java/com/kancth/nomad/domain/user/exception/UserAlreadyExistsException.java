package com.kancth.nomad.domain.user.exception;

import com.kancth.nomad.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {
    public UserAlreadyExistsException() {
        super("error.user.userAlreadyExists", HttpStatus.CONFLICT);
    }
}
