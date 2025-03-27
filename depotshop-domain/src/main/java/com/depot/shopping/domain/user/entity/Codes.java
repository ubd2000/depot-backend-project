package com.depot.shopping.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "codes")
@Getter
@NoArgsConstructor
public class Codes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "code_id")
  private long codeId;
  @Id
  @Column(name = "code_value")
  private String codeValue;
  @Column(name = "code_name")
  private String codeName;
  @Column(name = "code_desc")
  private String codeDesc;
  @Column(name = "code_order")
  private String codeOrder;
  @Column(name = "use_yn")
  private String useYn;
  @Column(name = "created_user")
  private String createdUser;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_user")
  private String updatedUser;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Codes(long codeId, String codeValue, String codeName, String codeDesc, String codeOrder, String useYn, String createdUser, LocalDateTime createdAt, String updatedUser, LocalDateTime updatedAt) {
    this.codeId = codeId;
    this.codeValue = codeValue;
    this.codeName = codeName;
    this.codeDesc = codeDesc;
    this.codeOrder = codeOrder;
    this.useYn = useYn;
    this.createdUser = createdUser;
    this.createdAt = createdAt;
    this.updatedUser = updatedUser;
    this.updatedAt = updatedAt;
  }
}
