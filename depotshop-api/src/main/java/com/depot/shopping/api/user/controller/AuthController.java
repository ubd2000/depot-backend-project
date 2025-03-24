package com.depot.shopping.api.user.controller;

import com.depot.shopping.api.user.dto.UserDto;
import com.depot.shopping.domain.user.entity.JwtDTO;
import com.depot.shopping.domain.user.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        Map<String, Object> loginMap = authService.login(UserDto.toEntity(userDto));

        return ResponseEntity.ok(loginMap);
    }

    @PostMapping("/check")
    public ResponseEntity<?> refreshTokenCheck(@RequestBody JwtDTO token) {
        Map<String, Object> tokenMap = authService.refreshTokenCheck(token);

        return ResponseEntity.ok(tokenMap);
    }
}