package com.kancth.nomad.domain.auth.controller;

import com.kancth.nomad.domain.auth.dto.SignInRequest;
import com.kancth.nomad.domain.auth.service.AuthService;
import com.kancth.nomad.domain.security.entity.JwtResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<JwtResponse> signIn(@RequestBody SignInRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.signIn(request, response));
    }
}
