package com.depot.shopping.api.user.controller;

import com.depot.shopping.api.user.dto.UserDto;
import com.depot.shopping.domain.user.entity.AuthResponse;
import com.depot.shopping.domain.user.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 로그인 처리
 */
@Tag(name = "Depot Shop", description = "Depot Shop Api")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        String token = authService.login(UserDto.toEntity(userDto));

        return ResponseEntity.ok(new AuthResponse(token));
    }
}