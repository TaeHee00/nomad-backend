package com.kancth.nomad.domain.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(timeToLive = 1209600000) // 2주
public class RefreshToken {
    // TODO: refreshToken으로 Token 재발급 시 기존 accessToken 블랙리스트 등록
    @Id
    private Long userId;
    private String refreshToken;
}
