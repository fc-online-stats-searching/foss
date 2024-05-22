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
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

import static com.foss.server.domain.metadata.MatchTypeData.OFFICIAL;

@RequiredArgsConstructor
@Service
@Slf4j
public class NexonApiWebClient {
    private final NexonClientUtil nexonClientUtil;
    private WebClient webClient;
    private final int MAX_RETRY = 5;
    private final int DELAY = 1;
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
                .retryWhen(Retry.fixedDelay(MAX_RETRY, Duration.ofSeconds(DELAY))
                        .jitter(0.5)
                        .doBeforeRetry(retrySignal -> {
                            log.info("[requestUserOuid] 시도 횟수: " + retrySignal.totalRetries() + ", 예외: " + retrySignal.failure());
                        }))
                .onErrorMap(e -> new NicknameNotFoundException());
    }

    public Mono<UserApiResponseDto> requestUserInfo(String ouid) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/user/basic")
                        .queryParam("ouid", ouid)
                        .build())
                .retrieve()
                .bodyToMono(UserApiResponseDto.class)
                .retryWhen(Retry.fixedDelay(MAX_RETRY, Duration.ofSeconds(DELAY))
                        .jitter(0.5)
                        .doBeforeRetry(retrySignal -> {
                            log.info("[requestUserInfo] 시도 횟수: " + retrySignal.totalRetries() + ", 예외: " + retrySignal.failure());
                        }))
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
                .bodyToMono(String[].class)
                .retryWhen(Retry.fixedDelay(MAX_RETRY, Duration.ofSeconds(DELAY))
                        .jitter(0.5)
                        .doBeforeRetry(retrySignal -> {
                            log.info("[requestMatchList] 시도 횟수: " + retrySignal.totalRetries() + ", 예외: " + retrySignal.failure());
                        }))
                .onErrorResume(e -> {
                    log.info("[requestMatchList] : " + "onErrorResume");
                    return Mono.error(e);
                });
    }

    public Mono<MatchDto> requestMatchInfo(String matchId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/match-detail")
                        .queryParam("matchid", matchId)
                        .build())
                .retrieve()
                .bodyToMono(MatchDto.class)
                .retryWhen(Retry.fixedDelay(MAX_RETRY, Duration.ofSeconds(DELAY))
                        .jitter(0.5)
                        .doBeforeRetry(retrySignal -> {
                            log.info("[requestMatchInfo] 시도 횟수: " + retrySignal.totalRetries() + ", 예외: " + retrySignal.failure());
                        }))
                .onErrorResume(e -> {
                    log.info("[requestMatchInfo] : " + "onErrorResume");
                    return Mono.error(e);
                });
    }



    public Mono<UserDivisionDto> requestUserDivision(String ouid) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/user/maxdivision")
                        .queryParam("ouid", ouid)
                        .build())
                .retrieve()
                .bodyToFlux(UserDivisionDto.class)
                .filter(o -> o.getMatchType() == OFFICIAL.getNumber())
                .next()
                .defaultIfEmpty(new UserDivisionDto())
                .retryWhen(Retry.backoff(MAX_RETRY, Duration.ofSeconds(DELAY))
                        .jitter(0.5)
                        .doBeforeRetry(retrySignal -> {
                            log.info("[requestUserDivision] 시도 횟수: " + retrySignal.totalRetries() + ", 예외: " + retrySignal.failure());
                        }))
                .onErrorResume(e -> {
                    log.info("[requestUserDivision] : " + "onErrorResume");
                    return Mono.error(e);
                });
    }

}
