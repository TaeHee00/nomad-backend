package com.kancth.nomad.global.interceptor;

import com.kancth.nomad.domain.security.annotation.Auth;
import com.kancth.nomad.domain.security.enums.AuthType;
import com.kancth.nomad.domain.security.exception.InsufficientPermissionException;
import com.kancth.nomad.domain.security.exception.InvalidTokenException;
import com.kancth.nomad.domain.security.service.JwtService;
import com.kancth.nomad.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        Auth auth = getAnnotation((HandlerMethod) handler, Auth.class);

        if (auth == null) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        AuthType authType = auth.authType();

        final String accessToken = this.getAuthToken(request);
        User user = jwtService.getUser(accessToken);

        switch(authType) {
            case USER -> this.checkPermission(user, AuthType.USER);
            case ADMIN -> this.checkPermission(user, AuthType.ADMIN);
        }

        request.setAttribute("currentUser", user);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationType) {
        return Optional.ofNullable(handlerMethod.getMethodAnnotation(annotationType))
                .orElse(handlerMethod.getBeanType().getAnnotation(annotationType));
    }

    private String getAuthToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.replace("Bearer ", "")).orElseThrow(InvalidTokenException::new);
        } else {
            throw new InvalidTokenException();
        }
    }

    private void checkPermission(User user, AuthType authType) {
        if (!(user.getAuthType().equals(AuthType.ADMIN) || user.getAuthType().equals(authType))) {
            throw new InsufficientPermissionException();
        }
    }
}
