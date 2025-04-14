package com.depot.shopping.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_seq", "sns_seq"})
}, name = "sns_users_mpng")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SnsUsersMpng {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "users_seq_id")
  private Users users;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sns_users_seq_id")
  private SnsUsers snsUsers;
}
