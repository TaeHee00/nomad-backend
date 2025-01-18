package com.kancth.nomad.domain.security.service;

import com.kancth.nomad.domain.security.entity.AccessToken;
import com.kancth.nomad.domain.security.entity.JwtResponse;
import com.kancth.nomad.domain.security.entity.RefreshToken;
import com.kancth.nomad.domain.user.dto.SignUpRequest;
import com.kancth.nomad.domain.user.entity.User;
import com.kancth.nomad.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JwtServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @DisplayName("GetUser By accessToken")
    @Test
    void getUser() {
        // Given
        // When
        // Then
    }

    @DisplayName("AccessToken 생성 Logic")
    @Test
    void genAccessToken() {
        // Given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .loginId("testId")
                .email("testEmail@nomad.com")
                .name("김철수")
                .nickname("테스터")
                .password("testPassword")
                .build();
        User user = userService.signUp(signUpRequest);

        // When
        AccessToken accessToken = jwtService.genAccessToken(user);

        // Then
        Jws<Claims> claims = jwtService.parseClaims(accessToken.token().replace("Bearer ", ""));
        Long userId = claims.getPayload().get("userId", Long.class);

        Assertions.assertThat(userId).isEqualTo(user.getId());
    }

    @DisplayName("RefreshToken 생성 Logic")
    @Test
    void genRefreshToken() {
        // Given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .loginId("testId")
                .email("testEmail@nomad.com")
                .name("김철수")
                .nickname("테스터")
                .password("testPassword")
                .build();
        User user = userService.signUp(signUpRequest);

        // When
        AccessToken accessToken = jwtService.genAccessToken(user);
        RefreshToken refreshToken = jwtService.genRefreshToken(user, accessToken);

        // Then
        RefreshToken findRefreshToken = jwtService.getRefreshTokenByUserId(user.getId());
        Assertions.assertThat(refreshToken.getRefreshToken()).isEqualTo(findRefreshToken.getRefreshToken());
        Assertions.assertThat(refreshToken.getUserId()).isEqualTo(findRefreshToken.getUserId());
    }

}