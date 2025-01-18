package com.kancth.nomad.domain.auth.service;

import com.kancth.nomad.domain.auth.dto.SignInRequest;
import com.kancth.nomad.domain.auth.exception.InvalidPasswordException;
import com.kancth.nomad.domain.security.JwtProperties;
import com.kancth.nomad.domain.security.entity.JwtResponse;
import com.kancth.nomad.domain.security.service.JwtService;
import com.kancth.nomad.domain.user.dto.SignUpRequest;
import com.kancth.nomad.domain.user.entity.User;
import com.kancth.nomad.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void signIn_Success() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .loginId("testId")
                .email("testEmail@nomad.com")
                .name("김철수")
                .nickname("테스터")
                .password("testPassword")
                .build();
        User testUser = userService.signUp(signUpRequest);

        SignInRequest signInRequest = SignInRequest.builder()
                .loginId(testUser.getLoginId())
                .password("testPassword")
                .build();

        MockHttpServletResponse response = new MockHttpServletResponse();
        JwtResponse result = authService.signIn(signInRequest, response);

        Assertions.assertThat(result).isNotNull();
        System.out.println(Arrays.toString(response.getCookies()));
    }

    @Test
    void signIn_InvalidPassword() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .loginId("testId")
                .email("testEmail@nomad.com")
                .name("김철수")
                .nickname("테스터")
                .password("testPassword")
                .build();
        User testUser = userService.signUp(signUpRequest);

        SignInRequest signInRequest = SignInRequest.builder()
                .loginId(testUser.getLoginId())
                .password("invalidPassword")
                .build();

        MockHttpServletResponse response = new MockHttpServletResponse();
        Assertions.assertThatThrownBy(() -> {
            JwtResponse result = authService.signIn(signInRequest, response);
        }).isInstanceOf(InvalidPasswordException.class);
    }
}
