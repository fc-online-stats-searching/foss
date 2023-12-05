package com.foss.server.domain.match;

import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class MatchDetail {

  private String id;
  private Float possession; // 점유율
  private Integer shootCount; // 슛 횟수
  private Integer effectiveShootCount; // 유효 슈팅 횟수
  private Integer passCount; // 패스 횟수
  private Float passSuccessRate; // 패스 성공 비율
  private Map<String, List<Player>> squads; // 선수단 정보


}
