package com.kancth.nomad.domain.security.entity;

import lombok.Builder;

import java.util.Date;

@Builder
public record AccessToken(
        String token,
        Date expiration
) {
}
