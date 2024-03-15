package com.foss.server.api;

import com.foss.server.api.dto.match.MatchDto;
import com.foss.server.api.dto.match.OuIdResponseDto;
import com.foss.server.api.dto.user.UserApiResponseDto;
import com.foss.server.exception.ManyRequestException;
import com.foss.server.exception.MatchIdNotFoundException;
import com.foss.server.exception.MemberNotFoundException;
import com.foss.server.exception.NicknameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Slf4j
public class NexonApiClient {
    private final RestTemplate restTemplate;
    @Value("${api-key}")
    private String API_KEY;
    private final static int maxRetries = 10;
    private final static int requestWait = 1000;
    private final static String API_HEADER = "x-nxopen-api-key";

    public String requestUserOuid(String nickname) {
        final String url = "https://open.api.nexon.com/fconline/v1/id?nickname={nickname}";

        try {
            OuIdResponseDto ouIdResponseDto = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), OuIdResponseDto.class,nickname).getBody();
            return ouIdResponseDto.getOuid();
        } catch (Exception e) {
            throw new NicknameNotFoundException();
        }

    }

    public UserApiResponseDto requestUserInfo(String ouid) {
        final String url = "https://open.api.nexon.com/fconline/v1/user/basic?ouid={ouid}";

        try {
            UserApiResponseDto userApiResponseDto = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), UserApiResponseDto.class, ouid).getBody();
            return userApiResponseDto;
        } catch (Exception e) {
            throw new MemberNotFoundException();
        }

    }

    public String[] requestMatchList(String ouid, int matchtype) {
        final String url = "https://open.api.nexon.com/fconline/v1/user/match?ouid={ouid}&matchtype={matchtype}&offset=0&limit=100";

        return restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), String[].class, ouid, matchtype).getBody();
    }

    @Async
    public MatchDto requestMatchAsync(String matchId) {
        final String url = "https://open.api.nexon.com/fconline/v1/match-detail?matchid={matchId}";

        try {
            MatchDto matchDto = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), MatchDto.class, matchId).getBody();
            return matchDto;
        } catch (Exception e) {
            throw new MatchIdNotFoundException();
        }
    }

    @Async
    public CompletableFuture<MatchDto> requestMatchInfoAsync(String matchId) {
        final String url = "https://open.api.nexon.com/fconline/v1/match-detail?matchid={matchId}";

        CompletableFuture<MatchDto> completableFuture = CompletableFuture.supplyAsync(() -> performRequestWithRetry(url, matchId));

        return completableFuture;
    }


    private MatchDto performRequestWithRetry(String url, String matchId) {
        for (int currentRetry = 0; currentRetry < maxRetries; currentRetry++) {
            try {
                return restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), MatchDto.class, matchId).getBody();
            } catch (HttpClientErrorException e) {
                handleHttpClientErrorException(e);
            }
        }
        throw new ManyRequestException();
    }

    private void handleHttpClientErrorException(HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
            try {
                Thread.sleep(requestWait);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        } else {
            throw e;
        }
    }


    private HttpEntity<String> getHttpEntity() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(API_HEADER, API_KEY);
        final HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        return entity;
    }
}