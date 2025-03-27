package com.depot.shopping.domain.user.service;

import com.depot.shopping.domain.RedisService;
import com.depot.shopping.domain.TokenService;
import com.depot.shopping.domain.user.entity.SnsUsers;
import com.depot.shopping.domain.user.repository.testSnsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * SNS 로그인 처리
 */
@Service
@RequiredArgsConstructor  // ✅ Lombok을 활용한 생성자 주입
public class OAuth2Service {

    private final testSnsUserRepository testSnsUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final RedisService redisService;

    private final RestTemplate restTemplate = new RestTemplate();

    /*******************************************************
     * 구글 설정
     *******************************************************/
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String google_id;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String google_secret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String google_redirect;

    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String google_scope;

    @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
    private String google_authUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String google_tokenUri;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String google_userInfoUri;

    /**
     * 구글 로그인 팝업
     */
    public String googlePage() {
        String state = UUID.randomUUID().toString(); // CSRF 보호용
        String url = UriComponentsBuilder.fromHttpUrl(google_authUri)
                .queryParam("client_id", google_id)
                .queryParam("redirect_uri", google_redirect)
                .queryParam("response_type", "code")
                .queryParam("scope", google_scope)
                .queryParam("state", state)
                .queryParam("access_type", "offline")
                .queryParam("prompt", "consent")
                .build()
                .toUriString();
        return url;
    }

    /**
     * 구글 SNS 로그인
     */
    public Map<String, Object> googleLogin(String code) {
        Map<String, Object> result = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", google_id);
        params.add("client_secret", google_secret);
        params.add("redirect_uri", google_redirect);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 1. 구글 Access Token 발급받기
        ResponseEntity<Map> response = restTemplate.postForEntity(
                google_tokenUri,
                request,
                Map.class
        );

        if(response.getBody() != null) {
            String googleAccessToken = response.getBody().get("access_token").toString();
            
            // Access Token 정상적으로 발급 받으면
            if(!"".equals(googleAccessToken)) {
                headers = new HttpHeaders();
                headers.setBearerAuth(googleAccessToken);

                HttpEntity<?> entity = new HttpEntity<>(headers);

                // 2. Access Token 으로 사용자 정보 요청
                response = restTemplate.exchange(
                        google_userInfoUri,
                        HttpMethod.GET,
                        entity,
                        Map.class
                );

                Map<String, Object> body = response.getBody();
                if(body != null && !body.isEmpty()) {
                    String id = body.get("sub").toString();

                    // 해당 조회된 고유 id로 회원여부 구분
                    SnsUsers user = testSnsUserRepository.findByOauthId(id);

                    if(user != null) {
                        // 회원

                    } else {
                        // 비회원

                    }

                    // 회원/비회원 별 처리 후 로그인처리 ㄱㄱ (토큰발급)
                    
                }
            }
        }


        return result;
    }
}