package com.foss.server.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.foss.server.api.NexonApiClient;
import com.foss.server.api.dto.match.MatchDto;
import com.foss.server.api.dto.user.UserDivisionDto;
import com.foss.server.dao.MatchRepository;
import com.foss.server.domain.match.Match;
import com.foss.server.domain.match.MatchDetail;
import com.foss.server.domain.match.Player;
import com.foss.server.dto.MatchMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncMatchServiceTest {

    @Mock
    private NexonApiClient nexonApiClient;
    @Mock
    private MatchRepository matchRepository;
    @InjectMocks
    private AsyncMatchService asyncMatchService;

    List<Match> matches = new ArrayList<>();
    MatchDto matchDto;

    @BeforeEach
    void setUp() throws IOException {
        // Match 1
        Match match1 = Match.builder()
                .id("match1")
                .matchType(1)
                .validation(true)
                .timestamp(LocalDateTime.of(2024, 4, 1, 20, 0))
                .result(Map.of("player1", "승", "player2", "패"))
                .goals(Map.of("player1", 2, "player2", 1))
                .team1(Arrays.asList("player1"))
                .team2(Arrays.asList("player2"))
                .matchDetails(Map.of(
                        "player1", new MatchDetail(55, 10, 7, 200, 75, Arrays.asList(
                                Player.builder().pid(101).position(1).grade(5).spRating(9.2).build())),
                        "player2", new MatchDetail(45, 8, 4, 180, 65, Arrays.asList(
                                Player.builder().pid(102).position(2).grade(4).spRating(8.5).build()))))
                .nickname(Map.of("player1", "PlayerOne", "player2", "PlayerTwo"))
                .division(Map.of(
                        "player1", new UserDivisionDto(1, 2, "2024-03-31"),
                        "player2", new UserDivisionDto(1, 1, "2024-03-30")))
                .build();

        // Match 2
        Match match2 = Match.builder()
                .id("match2")
                .matchType(2)
                .validation(false)
                .timestamp(LocalDateTime.of(2024, 4, 2, 15, 30))
                .result(Map.of("player3", "승", "player4", "패"))
                .goals(Map.of("player3", 3))
                .team1(Arrays.asList("player3"))
                .team2(Arrays.asList("player4"))
                .matchDetails(Map.of(
                        "player3", new MatchDetail(60, 12, 9, 220, 80, Arrays.asList(
                                Player.builder().pid(103).position(3).grade(5).spRating(9.5).build())),
                        "player4", new MatchDetail(40, 7, 3, 170, 60, Arrays.asList(
                                Player.builder().pid(104).position(4).grade(4).spRating(8.0).build()))))
                .nickname(Map.of("player3", "PlayerThree", "player4", "PlayerFour"))
                .division(Map.of(
                        "player3", new UserDivisionDto(2, 3, "2024-04-01"),
                        "player4", new UserDivisionDto(2, 2, "2024-03-29")))
                .build();

        matches = Arrays.asList(match1, match2);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // matchdto
        matchDto = objectMapper.readValue(new File("src/test/resources/match.json"), MatchDto.class);

    }

    @DisplayName("업데이트 매치리스트")
    @Test
    void updateMatchListDuplicated() throws ExecutionException, InterruptedException {
        //given
        final String ouid = "testOuid";
        final int matchType = 1;
        final int userDivision = 1;
        final String achievementDate = "2024-04-03";
        String[] matchList = {"match1", "match2"};
        List<String> idNotInList = Arrays.asList("id1", "id2");
        given(nexonApiClient.requestMatchList(ouid, matchType)).willReturn(matchList);
        given(matchRepository.findNonExistingIds(Arrays.asList(matchList))).willReturn(idNotInList);

        given(nexonApiClient.requestMatchInfoAsync(anyString()))
                .willReturn(CompletableFuture.completedFuture(matchDto));

        given(nexonApiClient.requestUserDivisionAsync(any()))
                .willReturn(CompletableFuture.completedFuture(new UserDivisionDto(matchType, userDivision, achievementDate)));

        //when
        CompletableFuture<Void> future = asyncMatchService.updateMatchListNotDuplicated(ouid, matchType);

        future.get();
        //then
        verify(nexonApiClient, times(1)).requestMatchList(anyString(), anyInt());
        verify(matchRepository, times(1)).findNonExistingIds(any());
        verify(nexonApiClient, times(matchList.length)).requestMatchInfoAsync(anyString());
        verify(nexonApiClient, atLeastOnce()).requestUserDivisionAsync(anyString());

        ArgumentCaptor<Match> matchCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository, times(matchList.length)).save(matchCaptor.capture());
        List<Match> capturedMatches = matchCaptor.getAllValues();

        for (Match capturedMatch : capturedMatches) {
            assertEquals(2, capturedMatch.getDivision().size());
            assertFalse(capturedMatch.getDivision().isEmpty(), "부문 정보가 비어있습니다.");

            capturedMatch.getDivision().forEach((nickname, division) -> {
                assertEquals(1, division.getDivision());
                assertTrue(matchDto.getMatchInfo().stream()
                                .anyMatch(matchInfo -> matchInfo.getOuid().equals(nickname)),
                        "nickname과 일치하는 ouid가 존재하지 않습니다.");
            });
        }

    }

    @DisplayName("업데이트 매치리스트, 업데이트할 매치가 존재하지 않는 경우")
    @Test
    void updateMatchListNotInMatchList() throws ExecutionException, InterruptedException {
        //given
        final String ouid = "testOuid";
        final int matchType = 1;
        String[] matchList = {};
        List<String> idNotInList = Arrays.asList();
        given(nexonApiClient.requestMatchList(ouid, matchType)).willReturn(matchList);
        given(matchRepository.findNonExistingIds(Arrays.asList(matchList))).willReturn(idNotInList);

        //when
        CompletableFuture<Void> future = asyncMatchService.updateMatchListNotDuplicated(ouid, matchType);

        future.get();
        //then
        verify(nexonApiClient, times(1)).requestMatchList(anyString(), anyInt());
        verify(matchRepository, times(1)).findNonExistingIds(any());
        verify(nexonApiClient, times(0)).requestMatchInfoAsync(anyString());
        verify(nexonApiClient, times(0)).requestUserDivisionAsync(anyString());

        ArgumentCaptor<Match> matchCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository, times(0)).save(matchCaptor.capture());
    }
}