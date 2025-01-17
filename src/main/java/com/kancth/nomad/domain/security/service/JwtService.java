package com.kancth.nomad.domain.security.service;

import com.kancth.nomad.domain.security.JwtProperties;
import com.kancth.nomad.domain.security.RefreshTokenRepository;
import com.kancth.nomad.domain.security.TokenNotFoundException;
import com.kancth.nomad.domain.security.entity.AccessToken;
import com.kancth.nomad.domain.security.entity.JwtResponse;
import com.kancth.nomad.domain.security.entity.RefreshToken;
import com.kancth.nomad.domain.user.entity.User;
import com.kancth.nomad.domain.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final byte[] keyBytes;
    private final Key signingKey;

    private final String ACCESS_PREFIX_STRING = "Bearer ";
    private final String ACCESS_HEADER_STRING = "Authorization";
    private final String REFRESH_HEADER_STRING = "RefreshToken";

    @Autowired
    public JwtService(UserService userService,
                      JwtProperties jwtProperties, RefreshTokenRepository refreshTokenRepository) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
        this.keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        this.signingKey = Keys.hmacShaKeyFor(this.keyBytes);
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public JwtResponse createJwt(User user) {
        AccessToken accessToken = this.genAccessToken(user);
        RefreshToken refreshToken = this.genRefreshToken(user, accessToken);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private AccessToken genAccessToken(User user) {
        Date expiration = new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenValiditySeconds());
        String token = ACCESS_PREFIX_STRING + Jwts.builder()
                .subject(Long.toString(user.getId()))
                .claim("userId", user.getId())
                .expiration(expiration)
                .signWith(signingKey)
                .compact();

        return AccessToken.builder()
                .token(token)
                .expiration(expiration)
                .build();
    }

    private RefreshToken genRefreshToken(User user, AccessToken accessToken) {
        Date expiration = new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenValiditySeconds());

        String refreshToken = Jwts.builder()
                .subject(Long.toString(user.getId()))
                .claim("userId", user.getId())
                .claim("accessToken", accessToken)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();

        // RefreshToken 생성 시 이전에 생성된 Refresh Token 폐기 처리
        refreshTokenRepository.deleteByUserId(user.getId());

        return refreshTokenRepository.save(RefreshToken.builder()
                .userId(user.getId())
                .refreshToken(refreshToken)
                .build());
    }

    private RefreshToken getRefreshTokenByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId)
                .orElseThrow(TokenNotFoundException::new);
    }
}
