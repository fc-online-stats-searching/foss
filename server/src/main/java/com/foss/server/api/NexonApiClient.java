package com.foss.server.api;

import com.foss.server.dto.api.match.MatchDto;
import com.foss.server.dto.api.match.OuIdResponseDto;
import com.foss.server.dto.api.user.UserApiResponseDto;
import com.foss.server.exception.ManyRequestException;
import com.foss.server.exception.MatchIdNotFoundException;
import com.foss.server.exception.MemberNotFoundException;
import com.foss.server.exception.NicknameNotFoundException;
import lombok.RequiredArgsConstructor;
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
public class NexonApiClient {
    private final RestTemplate restTemplate;
    @Value("${api-key}")
    private String API_KEY;
    private final static int maxRetries = 10;
    private final static int requestWait = 1000;
    private final static String API_HEADER = "x-nxopen-api-key";

    public String requestUserOuid(String nickname) {

        final String url = "https://open.api.nexon.com/fconline/v1/id?nickname={nickname}";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(API_HEADER, API_KEY);
        final HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        try {
            OuIdResponseDto ouIdResponseDto = restTemplate.exchange(url, HttpMethod.GET, entity, OuIdResponseDto.class,nickname).getBody();
            return ouIdResponseDto.getOuid();
        } catch (Exception e) {
            throw new NicknameNotFoundException();
        }

    }

    public UserApiResponseDto requestUserInfo(String ouid) {

        final String url = "https://open.api.nexon.com/fconline/v1/user/basic?ouid={ouid}";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(API_HEADER, API_KEY);
        final HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        try {
            UserApiResponseDto userApiResponseDto = restTemplate.exchange(url, HttpMethod.GET, entity, UserApiResponseDto.class, ouid).getBody();
            return userApiResponseDto;
        } catch (Exception e) {
            throw new MemberNotFoundException();
        }

    }

    public String[] requestMatchList(String ouid, int matchtype) {

        final String url = "https://open.api.nexon.com/fconline/v1/user/match?ouid={ouid}&matchtype={matchtype}&offset=0&limit=100";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(API_HEADER, API_KEY);
        final HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String[].class, ouid, matchtype).getBody();
    }

    public MatchDto requestMatchInfo(String matchId) {

        final String url = "https://open.api.nexon.com/fconline/v1/match-detail?matchid={matchId}";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(API_HEADER, API_KEY);
        final HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        try {
            MatchDto matchDto = restTemplate.exchange(url, HttpMethod.GET, entity, MatchDto.class, matchId).getBody();
            return matchDto;
        } catch (Exception e) {
            throw new MatchIdNotFoundException();
        }
    }

    @Async
    public CompletableFuture<MatchDto> requestMatchInfoAsync(String matchId) {

        final String url = "https://open.api.nexon.com/fconline/v1/match-detail?matchid={matchId}";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(API_HEADER, API_KEY);
        final HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        int currentRetry = 0;

        while (currentRetry < maxRetries) {
            try {
                MatchDto matchDto = restTemplate.exchange(url, HttpMethod.GET, entity, MatchDto.class, matchId).getBody();
                return CompletableFuture.completedFuture(matchDto);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    try {
                        Thread.sleep(requestWait);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    currentRetry++;
                } else {
                    throw e;
                }
            }
        }

        throw new ManyRequestException();
    }
}