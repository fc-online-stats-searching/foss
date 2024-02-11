package com.foss.server.dto;

import com.foss.server.domain.match.Match;
import com.foss.server.domain.match.MatchDetail;
import com.foss.server.domain.match.Player;
import com.foss.server.dto.api.match.MatchDetailDto;
import com.foss.server.dto.api.match.MatchDto;
import com.foss.server.dto.api.match.PlayerDto;
import com.foss.server.dto.match.MatchResponseDto;
import com.foss.server.dto.match.RelativeMatchResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchMapper {

    static final int FIRST = 0;
    static final int SECOND = 0;
    static final String WIN = "승";
    static final String TIE = "무";
    static final String LOSE = "패";
    public static Match matchToEntity(MatchDto matchDto) {

        Map<String, Integer> goals = new HashMap<>();
        List<String> team1 = new ArrayList<>();
        List<String> team2 = new ArrayList<>();
        Map<String, MatchDetail> matchDetails = new HashMap<>();
        Map<String, String> nickname = new HashMap<>();
        Map<String, String> result = new HashMap<>();
        boolean validation = false;

        for (int i = 0; i < matchDto.getMatchInfo().size(); i++) {
            MatchDetailDto matchDetail = matchDto.getMatchInfo().get(i);

            result.put(matchDetail.getOuid(), matchDetail.getMatchDetail().getMatchResult());
            goals.put(matchDetail.getOuid(), matchDetail.getShoot().getGoalTotal());
            nickname.put(matchDetail.getOuid(), matchDetail.getNickname());

            if (i == FIRST) {
                team1.add(matchDetail.getOuid());
            }

            if (i == SECOND) {
                team2.add(matchDetail.getOuid());
                validation = true;
            }

            List<Player> squads = new ArrayList<>();

            for (PlayerDto playerDto : matchDetail.getPlayer()) {
                squads.add(new Player(playerDto.getSpId(), playerDto.getSpPosition(), playerDto.getSpGrade(),playerDto.getStatus().getSpRating()));
            }

            MatchDetail matchEntityDetail = new MatchDetail(
                    matchDetail.getMatchDetail().getPossession(),
                    matchDetail.getShoot().getShootTotal(),
                    matchDetail.getShoot().getShootTotal(),
                    matchDetail.getPass().getPassTry(),
                    matchDetail.getPass().getPassSuccess(),
                    squads);

            matchDetails.put(matchDetail.getOuid(), matchEntityDetail);
        }



        Match match = Match.builder()
                .id(matchDto.getMatchId())
                .validation(validation)
                .matchType(matchDto.getMatchType())
                .timestamp(matchDto.getMatchDate())
                .nickname(nickname)
                .result(result)
                .goals(goals)
                .team1(team1)
                .team2(team2)
                .matchDetails(matchDetails)
                .build();

        return match;
    }

    public static RelativeMatchResponseDto getRelativeMatchResponseDto(String ouid, String opponentOuid, String nickname, List<Match> matches) {

        int totalWin = 0;
        int totalLose = 0;
        int totalGain = 0;
        int totalLoss = 0;
        int totalTie = 0;
        String opponentNickname = "";
        LocalDateTime lastDate = LocalDateTime.MIN;

        for (Match match : matches) {

            String result = match.getResult().get(ouid);
            int goal = match.getGoals().get(ouid);
            int opponentGoal = match.getGoals().get(opponentOuid);
            LocalDateTime date = match.getTimestamp();
            opponentNickname = match.getNickname().get(opponentOuid);

            if (result.contentEquals(WIN)) totalWin++;
            if (result.contentEquals(TIE)) totalTie++;
            if (result.contentEquals(LOSE)) totalLose++;

            totalGain += goal;
            totalLoss += opponentGoal;

            if (date.isAfter(lastDate)) {
                lastDate = date;
            }

        }

        return RelativeMatchResponseDto.builder()
                .lastDate(lastDate)
                .opponentNickname(opponentNickname)
                .win(totalWin)
                .tie(totalTie)
                .lose(totalLose)
                .gain(totalGain)
                .loss(totalLoss)
                .matchResponse(
                        matches.stream().map(match -> MatchResponseDto.of(ouid, opponentOuid, nickname, match))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
