package com.depot.shopping.api.user.controller;

import com.depot.shopping.api.user.dto.UserDto;
import com.depot.shopping.domain.user.entity.JwtDTO;
import com.depot.shopping.domain.user.service.AuthService;
import com.depot.shopping.domain.user.service.OAuth2Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * SNS 로그인
 */
@Tag(name = "Depot Shop", description = "Depot Shop Api")
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final AuthService authService;
    private final OAuth2Service oAuth2Service;

    @PostMapping("/login/kakao")
    public ResponseEntity<?> oAuthKakao(@RequestBody UserDto userDto) {
        Map<String, Object> loginMap = authService.login(UserDto.toEntity(userDto));

        return ResponseEntity.ok(loginMap);
    }

    @PostMapping("/login/naver")
    public ResponseEntity<?> oAuthNaver(@RequestBody JwtDTO token) {
        Map<String, Object> tokenMap = authService.refreshTokenCheck(token);

        return ResponseEntity.ok(tokenMap);
    }

    @GetMapping("/google")
    public ResponseEntity<?> oAuthGooglePage() {
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("url",oAuth2Service.googlePage());

        return ResponseEntity.ok(pageMap);
    }

    @GetMapping("/login/google")
    public ResponseEntity<?> oAuthGoogleLogin(String code) {
        Map<String, Object> loginMap = oAuth2Service.googleLogin(code);
        return ResponseEntity.ok(loginMap);
    }
}