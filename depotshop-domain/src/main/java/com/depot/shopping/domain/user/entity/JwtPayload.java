package com.depot.shopping.domain.user.entity;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayload {
    private Long userSeq;
    private boolean isSnsLogin;
    private String oauthId;
    private String oauthEmail;
}