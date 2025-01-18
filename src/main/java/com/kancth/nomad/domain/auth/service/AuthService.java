package com.kancth.nomad.domain.auth.service;

import com.kancth.nomad.domain.auth.dto.SignInRequest;
import com.kancth.nomad.domain.auth.exception.InvalidPasswordException;
import com.kancth.nomad.domain.security.JwtProperties;
import com.kancth.nomad.domain.security.entity.JwtResponse;
import com.kancth.nomad.domain.security.service.JwtService;
import com.kancth.nomad.domain.user.entity.User;
import com.kancth.nomad.domain.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;

    public JwtResponse signIn(SignInRequest request, HttpServletResponse response) {
        // NOTE: 해당 아이디 조회 불가 시 UserNotFoundException throw
        User user = userService.getUserByLoginId(request.loginId());
        // NOTE: 비밀번호 미 일치 시 InvalidPasswordException throw
        this.passwordCheck(request.password(), user.getPassword());

        // JWT 생성 & 반환
        JwtResponse jwt = jwtService.createJwt(user);
        // Cookie 등록
        this.addJwtCookie(jwt, response);

        return jwt;
    }

    private void addJwtCookie(JwtResponse jwt, HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("accessToken", URLEncoder.encode(jwt.accessToken().token(), StandardCharsets.UTF_8));
        // TODO: FrontEnd 완성 이후 Domain 변경
        accessTokenCookie.setDomain("localhost");
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge((int) jwtProperties.getAccessTokenValiditySeconds());
        // NOTE: http 에서도 작동하도록 설정
        accessTokenCookie.setSecure(false);

        Cookie refreshTokenCookie = new Cookie("refreshToken", URLEncoder.encode(jwt.refreshToken().getRefreshToken(), StandardCharsets.UTF_8));
        // TODO: FrontEnd 완성 이후 Domain 변경
        refreshTokenCookie.setDomain("localhost");
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge((int) jwtProperties.getRefreshTokenValiditySeconds());
        refreshTokenCookie.setSecure(false);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private void passwordCheck(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidPasswordException();
        }
    }
}
