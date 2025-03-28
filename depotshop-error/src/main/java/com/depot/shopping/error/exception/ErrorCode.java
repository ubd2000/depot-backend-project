package com.depot.shopping.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 토큰 에러코드
    TOKEN_INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "T0001", "유효한 토큰이 아닙니다."),

    // 유저 에러코드
    USER_NO_USER(HttpStatus.NOT_FOUND.value(), "U0001", "등록된 계정이 아닙니다."),
    USER_WRONG_PWD(HttpStatus.UNAUTHORIZED.value(), "U0002", "비밀번호가 틀렸습니다."),
    USER_INSERT(HttpStatus.INTERNAL_SERVER_ERROR.value(), "U0003", "회원등록 중 오류가 발생하였습니다."),
    SNS_USER_GET_INFO(HttpStatus.NOT_FOUND.value(), "U0004", "SNS 회원정보 조회 중 오류가 발생하였습니다."),

    // 서버 에러코드
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E0001", "서버 오류가 발생하였습니다."),
    // 요청 에러코드
    BAD_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E0002", "요청이 올바른 형식이 아닙니다.");

    private final int status;
    private final String errorCd;
    private final String description;

    ErrorCode(int status, String errorCd, String description) {
        this.status = status;
        this.errorCd = errorCd;
        this.description = description;
    }
}
