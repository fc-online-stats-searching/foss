package com.foss.server.domain.match;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.foss.server.api.dto.user.UserDivisionDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "matches")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Match {

  @Id
  private String id;
  private boolean validation;
  private int matchType; // 경기 종류
  private LocalDateTime timestamp; // 경기 일자
  private Map<String, String> result; // 승리 팀의 팀 구분자 (participants 에서 사용하는 key)
  private Map<String, Integer> goals; // 유저 별 득점 횟수
  private Map<String, String> nickname;
  private List<String> team1;
  private List<String> team2;
  private Map<String, MatchDetail> matchDetails; // 유저 별 매치 상세 정보 { key = 유저 accessId, val = 매치 상세 정보 }
  private Map<String, UserDivisionDto> division;

  @Builder

  public Match(String id, int matchType, boolean validation, LocalDateTime timestamp, Map<String, String> result,
               Map<String, Integer> goals, List<String> team1, List<String> team2,
               Map<String, MatchDetail> matchDetails, Map<String, String> nickname,Map<String,UserDivisionDto> division) {
    this.id = id;
    this.matchType = matchType;
    this.timestamp = timestamp;
    this.validation = validation;
    this.result = result;
    this.goals = goals;
    this.team1 = team1;
    this.team2 = team2;
    this.matchDetails = matchDetails;
    this.nickname = nickname;
    this.division = division;
  }

  public void addDivision(Map<String, UserDivisionDto> division) {
    this.division = division;
  }
}
