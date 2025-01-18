package com.kancth.nomad.domain.security.exception;

import com.kancth.nomad.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InsufficientPermissionException extends BaseException {
    public InsufficientPermissionException() {
        super("error.jwt.insufficientPermission", HttpStatus.FORBIDDEN);
    }
}