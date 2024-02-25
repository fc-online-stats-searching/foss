package com.foss.server.service;

import com.foss.server.api.NexonApiClient;
import com.foss.server.dao.MatchRepository;
import com.foss.server.domain.match.Match;
import com.foss.server.dto.MatchMapper;
import com.foss.server.dto.api.match.MatchDto;
import com.foss.server.exception.ManyRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncMatchService {

    private final NexonApiClient nexonApiClient;
    private final MatchRepository matchRepository;
    @Async
    public CompletableFuture<Void> updateMatchListNotDuplicated(String ouid, int matchType) {
        String[] matchList = nexonApiClient.requestMatchList(ouid, matchType);
        List<Match> matches = new ArrayList<>();
        List<String> idNotInList = matchRepository.findNonExistingIds(Arrays.asList(matchList));

        List<CompletableFuture<MatchDto>> futures = idNotInList.stream()
                .map(nexonApiClient::requestMatchInfoAsync).toList();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        return allFutures.thenAcceptAsync(unused -> {
            for (CompletableFuture<MatchDto> future : futures) {
                try {
                    MatchDto matchDto = future.get();
                    matches.add(MatchMapper.matchToEntity(matchDto));
                } catch (InterruptedException | ExecutionException e) {
                    throw new ManyRequestException();
                }
            }
            matchRepository.saveAll(matches);
        });

    }

}
