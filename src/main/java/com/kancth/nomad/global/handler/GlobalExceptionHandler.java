package com.kancth.nomad.global.handler;

import com.kancth.nomad.global.exception.BaseException;
import com.kancth.nomad.global.exception.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<Message> globalExceptionHandler(BaseException exception) {
        return ResponseEntity.status(exception.getHttpStatus())
                .body(Message.of(exception.getMessage()));
    }
}
