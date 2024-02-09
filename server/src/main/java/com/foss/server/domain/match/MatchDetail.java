package com.foss.server.domain.match;

import java.util.List;
import java.util.Map;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MatchDetail {

  private int possession; // 점유율
  private Integer shootCount; // 슛 횟수
  private Integer effectiveShootCount; // 유효 슈팅 횟수
  private Integer passCount; // 패스 횟수
  private int passSuccessRate; // 패스 성공 비율
  private List<Player> squads; // 선수단 정보 key=accessId, val = 해당 accessId 를 갖는 유저의 선수단 정보

}
