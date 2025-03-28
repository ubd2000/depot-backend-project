package com.depot.shopping.api.user.controller;

import com.depot.shopping.api.user.dto.UserDto;
import com.depot.shopping.domain.user.entity.JwtDTO;
import com.depot.shopping.domain.user.entity.Users;
import com.depot.shopping.domain.user.service.AuthService;
import com.depot.shopping.domain.user.service.OAuth2Service;
import com.depot.shopping.domain.user.service.UserService;
import com.depot.shopping.error.exception.CustomSnsUserGetInfoException;
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
    private final UserService userService;

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
        Map<String, Object> loginMap = null;
        // SNS 정보 조회
        Map<String, Object> accountInfo = oAuth2Service.googleLogin(code);

        if(accountInfo != null && !accountInfo.isEmpty()) {
            // SNS 고유 Id로 회원가입
            String id = accountInfo.get("sub").toString();
            Users userInfo = userService.snsSignUp(id);

//            // 가입된 정보로 로그인 처리
//            loginMap = authService.snsLogin(userInfo);
        } else {
            // 조회된 SNS 정보가 없으면
            throw new CustomSnsUserGetInfoException("FAIL_GET_SNS_INFO");
        }

        return ResponseEntity.ok(loginMap);
    }
}