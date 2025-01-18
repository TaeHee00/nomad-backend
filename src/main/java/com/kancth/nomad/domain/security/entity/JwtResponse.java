package com.kancth.nomad.domain.security.entity;

import lombok.Builder;

@Builder
public record JwtResponse(
        AccessToken accessToken,
        RefreshToken refreshToken
) {
}
