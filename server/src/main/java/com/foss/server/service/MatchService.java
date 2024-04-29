package com.foss.server.service;

import com.foss.server.api.NexonApiClient;
import com.foss.server.api.NexonApiWebClient;
import com.foss.server.api.dto.match.MatchDto;
import com.foss.server.api.dto.user.UserApiResponseDto;
import com.foss.server.api.dto.user.UserDivisionDto;
import com.foss.server.dao.MatchRepository;
import com.foss.server.dao.MemberRepository;
import com.foss.server.domain.match.Match;
import com.foss.server.domain.member.Member;
import com.foss.server.domain.metadata.MatchTypeData;
import com.foss.server.dto.MatchMapper;
import com.foss.server.dto.match.MatchResponseDto;
import com.foss.server.dto.match.RecentMatchDto;
import com.foss.server.dto.match.RelativeMatchDto;
import com.foss.server.dto.match.RelativeMatchResponseDto;
import com.foss.server.dto.member.MemberInfoResponseDto;
import com.foss.server.exception.ManyRequestException;
import com.foss.server.exception.MemberNotFoundException;
import com.foss.server.vo.OpponentMatches;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.foss.server.domain.metadata.MatchTypeData.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {

    private final NexonApiClient nexonApiClient;
    private final MemberRepository memberRepository;
    private final MatchRepository matchRepository;
    private final AsyncMatchService asyncMatchService;
    private static final String SORT_TIMESTAMP = "timestamp";
    private final NexonApiWebClient nexonApiWebClient;

    @Transactional
    public void refreshMatchList(String ouid) {
        List<Integer> allMatchTypes = getAllMatchTypes();
        List<CompletableFuture<Void>> updateTasks = new ArrayList<>();

        for (Integer matchType : allMatchTypes) {
            CompletableFuture<Void> updateTask = asyncMatchService.updateMatchListNotDuplicated(ouid, matchType);
            updateTasks.add(updateTask);
        }

        try {
            CompletableFuture.allOf(updateTasks.toArray(CompletableFuture[]::new)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new ManyRequestException();
        }
    }


    @Transactional
    public UserApiResponseDto refreshMatchListWebClient(String nickname) {
        String ouid = nexonApiWebClient.requestUserOuid(nickname).block();

        Mono<UserApiResponseDto> userInfo = nexonApiWebClient.requestUserInfo(ouid)
                .subscribeOn(Schedulers.boundedElastic());

        List<Integer> allMatchTypes = getAllMatchTypes();

        Flux<Match> allMatches = Flux.fromIterable(allMatchTypes)
                .flatMap(matchType -> nexonApiWebClient.requestMatchList(ouid, matchType)
                        .flatMapMany(Flux::fromArray)
                        .publishOn(Schedulers.parallel())
                        .flatMap(nexonApiWebClient::requestMatchInfo)
                        .flatMap(this::processMatch))
                .subscribeOn(Schedulers.parallel());


        Mono<List<Match>> savedMatches = allMatches.collectList()
                .doOnNext(matches -> matchRepository.saveAll(matches));


        Tuple2<UserApiResponseDto, List<Match>> tuple2 = Mono.zip(userInfo, savedMatches).block();

        return tuple2.getT1();
    }

    private Mono<Match> processMatch(MatchDto matchDto) {
        Match match = MatchMapper.matchToEntity(matchDto);

        return Flux.fromIterable(match.getNickname().keySet())
                .flatMap(nickname -> nexonApiWebClient.requestUserDivision(nickname)
                        .doOnNext(divisionDto -> match.addDivision(nickname, divisionDto)))
                .then(Mono.just(match));
    }

    public RecentMatchDto getRecentMatch(String nickname, int page, int typeNumber) {
        String ouid = nexonApiClient.requestUserOuid(nickname);
        Member member = memberRepository.findByAccessId(ouid).orElseThrow(MemberNotFoundException::new);

        List<Integer> matchTypes = determineMatchTypes(typeNumber);
        Page<Match> matches = findMatches(matchTypes, ouid, page, 10);

        return RecentMatchDto.builder()
                .memberInfo(MemberInfoResponseDto.from(member))
                .matchResponse(
                        matches.stream()
                                .map(match -> MatchResponseDto.of(ouid, nickname, match))
                                .collect(Collectors.toList()))
                .build();
    }

    public RelativeMatchDto getRelativeMatch(String nickname) {
        String ouid = nexonApiClient.requestUserOuid(nickname);
        Member member = memberRepository.findByAccessId(ouid).orElseThrow(MemberNotFoundException::new);

        List<Integer> matchTypes = determineMatchTypes(CLASSIC_1ON1.getNumber());
        Page<Match> matches = findMatches(matchTypes, ouid, 0, 30);

        OpponentMatches opponentMatches = new OpponentMatches(ouid, matches.getContent());
        List<RelativeMatchResponseDto> relativeMatchResponses = opponentMatches.getRelativeMatchResponses(ouid, nickname);

        return RelativeMatchDto.builder()
                .memberInfo(MemberInfoResponseDto.from(member))
                .relativeMatchResponse(relativeMatchResponses)
                .build();
    }

    private Page<Match> findMatches(List<Integer> matchTypes, String ouid, int page, int pageSize) {
        return matchRepository.findAllMatches(
                matchTypes,
                ouid,
                ouid,
                PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc(SORT_TIMESTAMP)))
        );
    }

}
