package com.depot.shopping.api.config;

import com.depot.shopping.domain.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<String> excludeUrls; // 🔹 필터에서 제외할 URL 목록

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // 🔹 제외할 URL이면 필터 통과
        if (excludeUrls.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenService.resolveToken(request);

        try {
            // 1️⃣ 토큰이 없는 경우 요청 중단
            if (token == null || token.isEmpty()) {
                throw new SecurityException("EMPTY JWT TOKEN");
            }

            // 2️⃣ 토큰 검증
            if (!tokenService.validateToken(token, false)) {
                throw new SecurityException("Invalid JWT token");
            }

            // 3️⃣ IP & User-Agent 검증 (세션 고정 공격 방지)
            String requestIp = request.getRemoteAddr();
            String requestUserAgent = request.getHeader("User-Agent");

            // 5️⃣ 정상적인 토큰이면 Authentication 설정 후 요청 진행
            // 필터에서는 accessToken 만 체크, refreshToken은 컨트롤러에서 체크
            Authentication authentication = tokenService.getAuthentication(token, false);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (SecurityException e) {
            sendErrorResponse(response, e.getMessage(), "T0001");
        } catch (Exception e) {
            sendErrorResponse(response, e.getMessage(), "E0001");
        }
    }

    /**
     * 🚀 JSON 형식으로 오류 응답 반환
     */
    private void sendErrorResponse(HttpServletResponse response, String message, String errorCd) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", 401);
        responseBody.put("errorCd", errorCd);
        responseBody.put("message", message);

        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}

