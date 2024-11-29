package com.irlix.traineeship.workplacebooking.controllers;

import com.irlix.traineeship.workplacebooking.payload.request.LoginRequest;
import com.irlix.traineeship.workplacebooking.payload.response.JwtResponse;
import com.irlix.traineeship.workplacebooking.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Контроллер для аутентификации пользователей")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    @Operation(summary = "Аутентификация пользователя", description = "Позволяет пользователю войти в приложение")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
