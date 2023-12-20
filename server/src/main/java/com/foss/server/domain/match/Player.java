package com.foss.server.domain.match;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "players")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

  @Id
  private String pid; // 선수 고유 id
  private String position; // 선수 포지션
  private String grade; // 선수 강화 등급
  private Float score; // 평점

  @Builder
  public Player(String pid, String position, String grade, Float score) {
    this.pid = pid;
    this.position = position;
    this.grade = grade;
    this.score = score;
  }
}
