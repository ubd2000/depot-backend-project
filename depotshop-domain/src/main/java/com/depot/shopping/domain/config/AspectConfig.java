package com.depot.shopping.domain.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class AspectConfig {
    @Around("execution(* com.depot.shopping.domain.user.service.UserService.snsSignUp(..)) || execution(* com.depot.shopping.domain.user.service.AuthService.login(..)) || execution(* com.depot.shopping.domain.user.service.AuthService.snsLogin(..))")
    public Object logUserAction(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        String methodName = joinPoint.getSignature().getName();
        String action = methodName.contains("SignUp") ? "JOIN" : "LOGIN";

        log.info("✅ [{}] 실행 - method={}, ip={}", action, methodName, getClientIp());

        return result;
    }

    private String getClientIp() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request.getRemoteAddr();
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}