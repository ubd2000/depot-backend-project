package com.depot.shopping.domain.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
  @Column(name = "seq_id")
  private long seqId;
  @Column(name = "user_id")
  private String userId;
  @Column(name = "user_passwd")
  private String userPasswd;
  @Column(name = "user_name")
  private String userName;
  @Column(name = "role")
  private String role;
  @Column(name = "status")
  private String status;
  @Column(name = "nickname")
  private String nickname;
  @Column(name = "phone")
  private String phone;
  @Column(name = "email")
  private String email;
  @Column(name = "identity_verified")
  private Boolean identityVerified;
  @Column(name = "created_user")
  private String createdUser;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_user")
  private String updatedUser;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Builder
  public Users(Long seqId, String userId, String userPasswd, String userName, String role, String status, String nickname, String phone, String email, Boolean identityVerified, String createdUser, LocalDateTime createdAt, String updatedUser, LocalDateTime updatedAt) {
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
}
