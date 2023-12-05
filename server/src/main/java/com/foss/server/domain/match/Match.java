package com.foss.server.domain.match;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "matchs")
public class Match {

  @Id
  private String id;
  private String matchType; // 경기 종류
  private LocalDateTime timestamp; // 경기 일자
  private String winner; // 승리 팀의 팀 구분자 (particiapnts에서 사용하는 key)
  private String mvpPlayer; // MVP 선수 id
  private Map<String, Integer> goals; // 유저 별 득점 횟수
  private Map<String, List<String>> participants; // 팀 별 유저 accessId { key = 팀 구분자 ( ex) team1, team2 ... ), val = 해당 팀의 소속 유저 accessId list }
  private Map<String, MatchDetail> matchDetails; // 유저 별 매치 상세 정보 { key = 유저 accessId, val = 매치 상세 정보 }
}
