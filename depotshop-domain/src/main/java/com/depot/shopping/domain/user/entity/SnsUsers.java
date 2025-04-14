package com.depot.shopping.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "sns_users",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_oauth_id_provider",
                columnNames = {"oauth_id", "oauth_provider"}
        )
)
@Getter
@NoArgsConstructor
public class SnsUsers {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sns_users_seq")
  @SequenceGenerator(name = "sns_users_seq", sequenceName = "sns_users_seq", allocationSize = 1)
  @Column(name = "seq_id")
  private long seqId;
  @Column(name = "oauth_id")
  private String oauthId;
  @Column(name = "oauth_provider")
  private String oauthProvider;
  @Column(name = "created_user")
  private String createdUser;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_user")
  private String updatedUser;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Builder
  public SnsUsers(long seqId, String oauthId, String oauthProvider, String createdUser, LocalDateTime createdAt, String updatedUser, LocalDateTime updatedAt) {
    this.seqId = seqId;
    this.oauthId = oauthId;
    this.oauthProvider = oauthProvider;
    this.createdUser = createdUser;
    this.createdAt = createdAt;
    this.updatedUser = updatedUser;
    this.updatedAt = updatedAt;
  }
}
