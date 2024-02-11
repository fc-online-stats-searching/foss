package com.foss.server.service;

import com.foss.server.api.NexonApiClient;
import com.foss.server.dao.MatchRepository;
import com.foss.server.dao.MemberRepository;
import com.foss.server.domain.match.Match;
import com.foss.server.domain.member.Member;
import com.foss.server.domain.metadata.MatchTypeData;
import com.foss.server.dto.MatchMapper;
import com.foss.server.dto.api.match.MatchDto;
import com.foss.server.dto.match.MatchResponseDto;
import com.foss.server.dto.match.RecentMatchDto;
import com.foss.server.dto.match.RelativeMatchDto;
import com.foss.server.dto.match.RelativeMatchResponseDto;
import com.foss.server.dto.member.MemberInfoResponseDto;
import com.foss.server.exception.ManyRequestException;
import com.foss.server.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final NexonApiClient nexonApiClient;
    private final MemberRepository memberRepository;
    private final MatchRepository matchRepository;

    static final String ERROR = "오류";

    @Transactional
    public void updateMatchListV0(String ouid, int matchType) {
        String[] matchList = nexonApiClient.requestMatchList(ouid, matchType);

        List<Match> matches = Arrays.stream(matchList)
                .map(nexonApiClient::requestMatchInfo)
                .filter(this::validateMatch)
                .map(MatchMapper::matchToEntity).toList();

        matchRepository.saveAll(matches);
    }
    @Transactional
    public void updateMatchListV1(String ouid, int matchType) throws ExecutionException, InterruptedException {
        String[] matchList = nexonApiClient.requestMatchList(ouid, matchType);
        List<Match> matches = new ArrayList<>();

        List<CompletableFuture<Void>> futures = Arrays.stream(matchList)
                .map(matchId -> CompletableFuture.runAsync(() -> {
                    if (!matchRepository.existsById(matchId)) {
                        CompletableFuture<MatchDto> matchDtoCompletableFuture = nexonApiClient.requestMatchInfoAsync(matchId);
                        MatchDto matchDto = null;
                        try {
                            matchDto = matchDtoCompletableFuture.get();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } catch (ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                        if(validateMatch(matchDto))
                            matches.add(MatchMapper.matchToEntity(matchDto));
                    }
                }))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        allOf.get();
        matchRepository.saveAll(matches);
    }

    @Transactional
    public void refreshMatchList(String ouid) {
        try {
            updateMatchListV1(ouid,MatchTypeData.OFFICIAL.getNumber());
            updateMatchListV1(ouid,MatchTypeData.CLASSIC_1ON1.getNumber());
        } catch (Exception e) {
            throw new ManyRequestException();
        }
    }

    public RecentMatchDto getRecentMatch(String nickname, int page, int typeNumber) {
        String ouid = nexonApiClient.requestUserOuid(nickname);
        Member member = memberRepository.findByAccessId(ouid).orElseThrow(MemberNotFoundException::new);

        List<Integer> matchTypes = new ArrayList<>();

        if (typeNumber== MatchTypeData.ALL.getNumber()) {
            matchTypes.addAll(MatchTypeData.getAllMatchType());
        }else{
            matchTypes.add(typeNumber);
        }

        Page<Match> matches = matchRepository.findAllMatches(
                matchTypes,
                ouid,
                ouid,
                PageRequest.of(page, 10, Sort.by(Sort.Order.desc("timestamp")))
        );

        return RecentMatchDto.builder()
                .memberInfo(MemberInfoResponseDto.from(member))
                .matchResponse(matches.stream()
                        .map(match -> MatchResponseDto.of(ouid, nickname, match))
                        .collect(Collectors.toList()))
                .build();
    }

    public RelativeMatchDto getRelativeMatch(String nickname) {
        String ouid = nexonApiClient.requestUserOuid(nickname);

        Member member = memberRepository.findByAccessId(ouid).orElseThrow(MemberNotFoundException::new);

        Page<Match> matches = matchRepository.findAllMatches(
                List.of(MatchTypeData.CLASSIC_1ON1.getNumber()),
                ouid,
                ouid,
                PageRequest.of(0, 30, Sort.by(Sort.Order.desc("timestamp")))
        );

        Map<String, List<Match>> opponentOuids = getOpponentOuid(ouid, matches.getContent());

        List<RelativeMatchResponseDto> relativeMatchResponse = new ArrayList<>();

        for (Map.Entry<String, List<Match>> entry : opponentOuids.entrySet()) {
            String opponentOuid = entry.getKey();
            List<Match> matchList = entry.getValue();

            relativeMatchResponse.add(MatchMapper.getRelativeMatchResponseDto(ouid,opponentOuid,nickname,matchList));

        }

        return RelativeMatchDto.builder()
                .memberInfo(MemberInfoResponseDto.from(member))
                .relativeMatchResponse(relativeMatchResponse)
                .build();
    }


    private Map<String, List<Match>> getOpponentOuid(String ouid, List<Match> matches) {
        Map<String, List<Match>> opponentOuids = new HashMap<>();

        for (Match match : matches) {
            List<String> team1 = match.getTeam1();
            List<String> team2 = match.getTeam2();

            // 사용자의 Ouid가 team1 또는 team2에 있는지 확인
            if (team1.contains(ouid)) {
                // 찾았다면, team2를 반복하면서 상대방 Ouid를 저장
                for (String opponent : team2) {
                    opponentOuids.computeIfAbsent(opponent, key -> new ArrayList<>()).add(match);
                }
            } else if (team2.contains(ouid)) {
                // 찾았다면, team1을 반복하면서 상대방 Ouid를 저장
                for (String opponent : team1) {
                    opponentOuids.computeIfAbsent(opponent, key -> new ArrayList<>()).add(match);
                }
            }
        }

        return opponentOuids;
    }

    private boolean validateMatch(MatchDto matchDto) {
        if (matchDto.getMatchInfo().get(0).getMatchDetail().getMatchResult().equals(ERROR) ||
                matchDto.getMatchInfo().size() == 1) {
            return false;
        }

        return true;
    }

}
