package com.depot.shopping.domain.user.entity;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {
    private String accessToken;
    private String refreshToken;
}