package com.foss.server.dto.match;

import com.foss.server.api.dto.user.UserDivisionDto;
import com.foss.server.domain.match.Match;
import com.foss.server.domain.match.MatchDetail;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResponseDto{
    private int matchType; // 경기 종류
    private LocalDateTime timestamp; // 경기 일자
    private String result; // 경기 결과
    private Map<String, Integer> goals; // 유저 별 득점 횟수
    private String nickName;
    private String opponentNickname; // 상대 유저 닉네임
    private UserDivisionDto opponentDivision; // 상대 유저 탑티어
    private MatchDetail matchDetail; // 해당 유저 매치상세

    public static MatchResponseDto of(String ouid, String nickname, Match match) {
        String opponentOuid = "";
        for (Map.Entry<String, Integer> entry : match.getGoals().entrySet()) {
            String opponent = entry.getKey();

            if (!opponent.contentEquals(ouid)) {
                opponentOuid = opponent;
            }
        }

        return MatchResponseDto.builder()
                .matchType(match.getMatchType())
                .timestamp(match.getTimestamp())
                .result(match.getResult().get(ouid))
                .goals(nicknameScoreMapOf(match))
                .nickName(nickname)
                .opponentNickname(match.getNickname().get(opponentOuid))
                .opponentDivision(match.getDivision().get(opponentOuid))
                .matchDetail(match.getMatchDetails().get(ouid))
                .build();
    }

    public static MatchResponseDto of(String ouid, String opponentOuid, String ninckname, Match match) {
        return MatchResponseDto.builder()
                .matchType(match.getMatchType())
                .timestamp(match.getTimestamp())
                .result(match.getResult().get(ouid))
                .goals(nicknameScoreMapOf(match))
                .nickName(ninckname)
                .opponentNickname(match.getNickname().get(opponentOuid))
                .opponentDivision(match.getDivision().get(opponentOuid))
                .matchDetail(match.getMatchDetails().get(ouid))
                .build();
    }


    public static Map<String, Integer> nicknameScoreMapOf(Match match) {
        Map<String, String> ouidNickname = match.getNickname();
        Map<String, Integer> ouidScore = match.getGoals();
        Map<String, Integer> nicknameScore = new HashMap<>();

        for (Map.Entry<String, Integer> entry : ouidScore.entrySet()) {
            Integer score = entry.getValue();
            String ouid = entry.getKey();

            nicknameScore.put(ouidNickname.get(ouid), score);
        }

        return nicknameScore;
    }
}
