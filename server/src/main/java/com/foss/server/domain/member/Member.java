package com.foss.server.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mb_id", nullable = false)
  private Long id;

  @Setter(AccessLevel.PUBLIC)
  @NotBlank(message = "사용자 닉네임은 1자 이상이어야 합니다")
  @Column(name = "mb_username", nullable = false)
  private String username;

  @NotBlank(message = "accessid는 필수 입니다")
  @Column(name = "mb_access_id", nullable = false)
  private String accessId;

  @Setter(AccessLevel.PUBLIC)
  @NotNull(message = "사용자 레벨은 필수입니다")
  @Column(name = "mb_level", nullable = false)
  private Integer level;

  @Column(name = "mb_renewal", nullable = false)
  @Setter(AccessLevel.PUBLIC)
  private LocalDateTime renewal;

  @Builder
  public Member(String username, String accessId, Integer level, LocalDateTime renewal) {
    this.renewal = renewal;
    this.username = username;
    this.accessId = accessId;
    this.level = level;
  }
}
