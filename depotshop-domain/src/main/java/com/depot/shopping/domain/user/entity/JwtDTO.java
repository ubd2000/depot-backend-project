package com.depot.shopping.domain.user.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private String accessToken;
    private String refreshToken;
}