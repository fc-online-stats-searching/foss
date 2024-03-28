package com.foss.server.domain.match;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

  private int pid; // 선수 고유 id
  private int position; // 선수 포지션
  private int grade; // 선수 강화 등급
  private double spRating;

  @Builder
  public Player(int pid, int position, int grade,double spRating) {
    this.pid = pid;
    this.position = position;
    this.grade = grade;
    this.spRating = spRating;
  }
}
