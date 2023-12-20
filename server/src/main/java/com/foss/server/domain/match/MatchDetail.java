package com.foss.server.domain.match;

import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "match_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchDetail {

  @Id
  private String id;
  private Float possession; // 점유율
  private Integer shootCount; // 슛 횟수
  private Integer effectiveShootCount; // 유효 슈팅 횟수
  private Integer passCount; // 패스 횟수
  private Float passSuccessRate; // 패스 성공 비율
  private Map<String, List<Player>> squads; // 선수단 정보 key=accessId, val = 해당 accessId 를 갖는 유저의 선수단 정보

  @Builder
  public MatchDetail(String id, Float possession, Integer shootCount, Integer effectiveShootCount,
      Integer passCount, Float passSuccessRate, Map<String, List<Player>> squads) {
    this.id = id;
    this.possession = possession;
    this.shootCount = shootCount;
    this.effectiveShootCount = effectiveShootCount;
    this.passCount = passCount;
    this.passSuccessRate = passSuccessRate;
    this.squads = squads;
  }
}
