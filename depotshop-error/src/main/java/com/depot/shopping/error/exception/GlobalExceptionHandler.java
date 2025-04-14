package com.depot.shopping.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 컨트롤러에서 사용되는 예외처리
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ErrorCode.BAD_REQUEST.getStatus());
        response.put("errorCd", ErrorCode.BAD_REQUEST.getErrorCd());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 유저 정보 조회 실패(정보 없음)
     */
    @ExceptionHandler(CustomUserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomNotFoundException(CustomUserNotFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ErrorCode.USER_NO_USER.getStatus());
        response.put("errorCd", ErrorCode.USER_NO_USER.getErrorCd());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 유저 비밀번호 틀림
     */
    @ExceptionHandler(CustomUserWrongPwdException.class)
    public ResponseEntity<Map<String, Object>> handleCustomUserWrongPwdException(CustomUserWrongPwdException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ErrorCode.USER_WRONG_PWD.getStatus());
        response.put("errorCd", ErrorCode.USER_WRONG_PWD.getErrorCd());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * SNS 회원정보 추출 실패
     */
    @ExceptionHandler(CustomSnsUserGetInfoException.class)
    public ResponseEntity<Map<String, Object>> handleCustomSnsUserGetInfoException(CustomSnsUserGetInfoException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ErrorCode.SNS_USER_GET_INFO.getStatus());
        response.put("errorCd", ErrorCode.SNS_USER_GET_INFO.getErrorCd());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * SNS 로그인 거부
     */
    @ExceptionHandler(CustomSnsUserRejectException.class)
    public ResponseEntity<Map<String, Object>> handleCustomSnsUserRejectException(CustomSnsUserRejectException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ErrorCode.SNS_USER_REJECT.getStatus());
        response.put("errorCd", ErrorCode.SNS_USER_REJECT.getErrorCd());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 회원 등록시 오류
     */
    @ExceptionHandler(CustomUserInsertException.class)
    public ResponseEntity<Map<String, Object>> handleCustomUserInsertException(CustomUserInsertException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ErrorCode.USER_INSERT.getStatus());
        response.put("errorCd", ErrorCode.USER_INSERT.getErrorCd());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 만료된 리프레시 토큰
     */
    @ExceptionHandler(CustomInvalidRefreshTokenException.class)
    public ResponseEntity<Map<String, Object>> handleCustomInvalidRefreshTokenException(CustomInvalidRefreshTokenException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ErrorCode.TOKEN_INVALID_REFRESH_TOKEN.getStatus());
        response.put("errorCd", ErrorCode.TOKEN_INVALID_REFRESH_TOKEN.getErrorCd());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
        response.put("errorCd", ErrorCode.INTERNAL_SERVER_ERROR.getErrorCd());
        response.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
