package com.foss.server.domain.match;

import lombok.Getter;

@Getter
public class Player {
  private String id; // 선수 고유 id
  private String position; // 선수 포지션
  private String grade; // 선수 강화 등급
  private Float score; // 평점
}
