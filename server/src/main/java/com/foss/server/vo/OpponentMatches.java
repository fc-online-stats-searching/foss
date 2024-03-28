package com.foss.server.vo;

import com.foss.server.domain.match.Match;
import com.foss.server.dto.MatchMapper;
import com.foss.server.dto.match.RelativeMatchResponseDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OpponentMatches {

    private Map<String, List<Match>> opponentMatches;

    public OpponentMatches(String ouid, List<Match> matches) {
        this.opponentMatches = new HashMap<>();
        extractOpponentMatches(ouid, matches);
    }

    public List<RelativeMatchResponseDto> getRelativeMatchResponses(String ouid, String nickname) {
        return opponentMatches.entrySet().stream()
                .map(entry -> MatchMapper.getRelativeMatchResponseDto(ouid, entry.getKey(), nickname, entry.getValue()))
                .collect(Collectors.toList());
    }

    private void extractOpponentMatches(String ouid, List<Match> matches) {
        for (Match match : matches) {
            List<String> team1 = match.getTeam1();
            List<String> team2 = match.getTeam2();

            addOpponentMatches(ouid, team1, team2, match);
            addOpponentMatches(ouid, team2, team1, match);
        }
    }

    private void addOpponentMatches(String ouid, List<String> team, List<String> opponentTeam, Match match) {
        if (team.contains(ouid)) {
            for (String opponent : opponentTeam) {
                opponentMatches.computeIfAbsent(opponent, key -> new ArrayList<>()).add(match);
            }
        }
    }

}
