package com.foss.server.api;

import com.foss.server.api.dto.match.MatchDto;
import com.foss.server.api.dto.match.OuIdResponseDto;
import com.foss.server.api.dto.user.UserApiResponseDto;
import com.foss.server.api.dto.user.UserDivisionDto;
import com.foss.server.config.NexonClientUtil;
import com.foss.server.exception.DivisionNotFoundException;
import com.foss.server.exception.ManyRequestException;
import com.foss.server.exception.MemberNotFoundException;
import com.foss.server.exception.NicknameNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.foss.server.domain.metadata.MatchTypeData.OFFICIAL;

@RequiredArgsConstructor
@Service
@Slf4j
public class NexonApiWebClient {
    private final NexonClientUtil nexonClientUtil;
    private WebClient webClient;
    @PostConstruct
    public void init() {
        webClient = nexonClientUtil.createNexonWebClient();
    }
    public Mono<String> requestUserOuid(String nickname) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/id")
                        .queryParam("nickname", nickname)
                        .build())
                .retrieve()
                .bodyToMono(OuIdResponseDto.class)
                .map(OuIdResponseDto::getOuid)
                .onErrorMap(e -> new NicknameNotFoundException());
    }

    public Mono<UserApiResponseDto> requestUserInfo(String ouid) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/user/basic")
                        .queryParam("ouid", ouid)
                        .build())
                .retrieve()
                .bodyToMono(UserApiResponseDto.class)
                .onErrorMap(e -> new MemberNotFoundException());
    }

    public Mono<String[]> requestMatchList(String ouid, int matchtype) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/user/match")
                        .queryParam("ouid", ouid)
                        .queryParam("matchtype", matchtype)
                        .queryParam("offset", 0)
                        .queryParam("limit", 100)
                        .build())
                .retrieve()
                .bodyToMono(String[].class);
    }

    public Mono<MatchDto> requestMatchInfo(String matchId) {
        log.info("매치 요청 :" + matchId);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/match-detail")
                        .queryParam("matchid", matchId)
                        .build())
                .retrieve()
                .bodyToMono(MatchDto.class)
                .doOnError(HttpClientErrorException.TooManyRequests.class, e -> {
                    throw new ManyRequestException();
                });
    }

    public Mono<UserDivisionDto> requestUserDivision(String ouid) {
        log.info("유저 티어 요청 :" + ouid);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/user/maxdivision")
                        .queryParam("ouid", ouid)
                        .build())
                .retrieve()
                .bodyToFlux(UserDivisionDto.class)
                .filter(o -> o.getMatchType() == OFFICIAL.getNumber())
                .next()
                .defaultIfEmpty(new UserDivisionDto())
                .doOnError(e -> {
                    throw new DivisionNotFoundException();
                });
    }

}
