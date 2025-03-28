package com.depot.shopping.api.user.dto;

import com.depot.shopping.domain.user.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author DongMin Kim
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private long seqId;
    private String userId;
    private String userPasswd;
    private String userName;
    private String role;
    private String status;
    private String nickname;
    private String phone;
    private String email;
    private Boolean identityVerified;
    private String createdUser;
    private LocalDateTime createdAt;
    private String updatedUser;
    private LocalDateTime updatedAt;

    @Builder
    public UserDto(long seqId, String userId, String userPasswd, String userName, String role, String status, String nickname, String phone, String email, String oauthId, String oauthProvider, Boolean identityVerified, String createdUser, LocalDateTime createdAt, String updatedUser, LocalDateTime updatedAt) {
        this.seqId = seqId;
        this.userId = userId;
        this.userPasswd = userPasswd;
        this.userName = userName;
        this.role = role;
        this.status = status;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.identityVerified = identityVerified;
        this.createdUser = createdUser;
        this.createdAt = createdAt;
        this.updatedUser = updatedUser;
        this.updatedAt = updatedAt;
    }

    public static Users toEntity(UserDto userDto) {
        return Users.builder()
//                .seqId(userDto.getSeqId())
                .userId(userDto.getUserId())
                .userPasswd(userDto.getUserPasswd())
                .userName(userDto.getUserName())
                .role(userDto.getRole())
                .status(userDto.getStatus())
                .nickname(userDto.getNickname())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .identityVerified(userDto.getIdentityVerified())
                .createdUser(userDto.getCreatedUser())
                .createdAt(userDto.getCreatedAt())
                .updatedUser(userDto.getUpdatedUser())
                .updatedAt(userDto.getUpdatedAt())
                .build();
    }
}
