package com.kancth.nomad.domain.security;

import com.kancth.nomad.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends BaseException {
    public TokenNotFoundException() {
        super("error.jwt.tokenNotFound", HttpStatus.NOT_FOUND);
    }
}
