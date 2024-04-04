package com.foss.server.service;

import com.foss.server.api.NexonApiClient;
import com.foss.server.api.dto.user.UserDivisionDto;
import com.foss.server.dao.MatchRepository;
import com.foss.server.domain.match.Match;
import com.foss.server.dto.MatchMapper;
import com.foss.server.api.dto.match.MatchDto;
import com.foss.server.exception.ManyRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncMatchService {
    private final NexonApiClient nexonApiClient;
    private final MatchRepository matchRepository;
    private final ConcurrentHashMap<String, CompletableFuture<UserDivisionDto>> divisionFutureMap = new ConcurrentHashMap<>();

    @Async
    public CompletableFuture<Void> updateMatchListNotDuplicated(String ouid, int matchType) {
        String[] matchList = nexonApiClient.requestMatchList(ouid, matchType);
        List<String> idNotInList = matchRepository.findNonExistingIds(Arrays.asList(matchList));

        List<CompletableFuture<Void>> matchFutures = idNotInList.stream()
                .map(id -> processMatchInfo(id))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(matchFutures.toArray(new CompletableFuture[0]))
                .thenRun(() -> log.info("모든 매치 정보와 부문 정보가 성공적으로 업데이트되었습니다."));
    }

    private CompletableFuture<Void> processMatchInfo(String id) {
        return nexonApiClient.requestMatchInfoAsync(id)
                .thenCompose(matchDto -> {
                    Match match = MatchMapper.matchToEntity(matchDto);
                    return processDivisionInfo(match);
                });
    }

    private CompletableFuture<Void> processDivisionInfo(Match match) {
        List<CompletableFuture<Void>> divisionFutures = match.getNickname().keySet().stream()
                .map(nickname -> divisionFutureMap.computeIfAbsent(nickname, k -> nexonApiClient.requestUserDivisionAsync(k))
                        .thenAccept(divisionDto -> match.addDivision(nickname, divisionDto)))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(divisionFutures.toArray(new CompletableFuture[0]))
                .thenRun(() -> matchRepository.save(match));
    }

}