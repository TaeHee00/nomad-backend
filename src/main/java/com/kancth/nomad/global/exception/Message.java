package com.kancth.nomad.global.exception;

import lombok.Builder;

@Builder
public record Message(
        String message
) {
    public static Message of(String message) {
        return Message.builder()
                .message(message)
                .build();
    }
}
