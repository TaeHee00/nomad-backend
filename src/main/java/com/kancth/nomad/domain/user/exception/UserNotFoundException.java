package com.kancth.nomad.domain.user.exception;

import com.kancth.nomad.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super("error.user.userNotFound", HttpStatus.NOT_FOUND);
    }
}
