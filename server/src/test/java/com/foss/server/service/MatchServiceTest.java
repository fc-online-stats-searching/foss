package com.foss.server.service;

import com.foss.server.api.NexonApiClient;
import com.foss.server.api.dto.user.UserDivisionDto;
import com.foss.server.dao.MatchRepository;
import com.foss.server.dao.MemberRepository;
import com.foss.server.domain.match.Match;
import com.foss.server.domain.match.MatchDetail;
import com.foss.server.domain.match.Player;
import com.foss.server.domain.member.Member;
import com.foss.server.domain.metadata.MatchTypeData;
import com.foss.server.dto.match.RecentMatchDto;
import com.foss.server.dto.match.RelativeMatchDto;
import com.foss.server.exception.ManyRequestException;
import com.foss.server.exception.MemberNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.foss.server.domain.metadata.MatchTypeData.determineMatchTypes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class MatchServiceTest {

    @InjectMocks
    private MatchService matchService;
    @Mock
    private AsyncMatchService asyncMatchService;
    @Mock
    private NexonApiClient nexonApiClient;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MatchRepository matchRepository;

    private List<Match> matches;

    @BeforeEach
    void setup() {
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

        // Match 3
        Match match3 = Match.builder()
                .id("match3")
                .matchType(MatchTypeData.OFFICIAL.getNumber())
                .validation(true)
                .timestamp(LocalDateTime.of(2024, 4, 3, 18, 45))
                .result(Map.of("player5", "승", "player6", "패"))
                .goals(Map.of("player5", 2, "player6", 1))
                .team1(Arrays.asList("player5"))
                .team2(Arrays.asList("player6"))
                .matchDetails(Map.of(
                        "player5", new MatchDetail(65, 14, 11, 230, 85, Arrays.asList(
                                Player.builder().pid(105).position(5).grade(5).spRating(9.8).build())),
                        "player6", new MatchDetail(35, 5, 2, 150, 50, Arrays.asList(
                                Player.builder().pid(106).position(6).grade(3).spRating(7.5).build()))))
                .nickname(Map.of("player5", "PlayerFive", "player6", "PlayerSix"))
                .division(Map.of(
                        "player5", new UserDivisionDto(2, 3, "2024-04-01"),
                        "player6", new UserDivisionDto(2, 2, "2024-03-29")))
                .build();

        matches = Arrays.asList(match1, match2, match3);
    }


    @DisplayName("성공적으로 매치리스트 갱신")
    @Test
    void refreshMatchListShouldCompleteSuccessfully() {
        // given
        String ouid = "testOuid";
        given(asyncMatchService.updateMatchListNotDuplicated(anyString(), anyInt()))
                .willReturn(CompletableFuture.completedFuture(null));

        // when
        assertDoesNotThrow(() -> matchService.refreshMatchList(ouid));

        // then
    }

    @DisplayName("매치리스트 갱신중 예외 발생")
    @Test
    void refreshMatchListShouldThrowManyRequestExceptionOnExecutionException() throws Exception {
        // given
        String ouid = "testOuid";
        given(asyncMatchService.updateMatchListNotDuplicated(anyString(), anyInt()))
                .willReturn(CompletableFuture.failedFuture(new ExecutionException(new InterruptedException())));

        // when & then
        assertThrows(ManyRequestException.class, () -> matchService.refreshMatchList(ouid));
    }

    @DisplayName("최근 전적 불러오기할 시 Mapping이 잘되서 반환되는 지 확인")
    @Test
    void getRecentMatch() {
        //given
        final String nickname = "testNickname";
        final String ouid = "testOuid";
        final int level = 1;
        final int page = 0;
        final int pageSize = 10;
        final LocalDateTime renewal =LocalDateTime.now();
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Member member = new Member(nickname, ouid, level, renewal);
        given(nexonApiClient.requestUserOuid(nickname)).willReturn(ouid);
        given(memberRepository.findByAccessId(ouid)).willReturn(Optional.of(member));
        given(matchRepository.findAllMatches(anyList(), anyString(), anyString(), any(Pageable.class)))
                .willReturn(new PageImpl<>(matches));
        //when
        RecentMatchDto result = matchService.getRecentMatch(nickname, 0, MatchTypeData.OFFICIAL.getNumber());

        //then
        assertEquals(result.getMatchResponse().size(), 3);

        assertEquals(result.getMemberInfo().getNickname(), nickname);
        assertEquals(result.getMemberInfo().getOuid(), ouid);
        assertEquals(result.getMemberInfo().getLevel(), level);
        assertEquals(result.getMemberInfo().getRenewal(), renewal);

        assertEquals(1, result.getMatchResponse().get(0).getMatchType());
        System.out.println(result.getMatchResponse().get(0).getGoals().toString());
        assertTrue(result.getMatchResponse().get(0).getGoals().containsKey("PlayerTwo"));
        assertEquals(2, result.getMatchResponse().get(0).getGoals().get("PlayerOne"));
        assertEquals(result.getMatchResponse().get(0).getTimestamp(),matches.get(0).getTimestamp());
    }

    @DisplayName("최근 전적 불러올 때 지정한 typeNumber만 나오는 지 확인")
    @Test
    void getRecentMatchByTypeNumber() {
        //given
        final String nickname = "testNickname";
        final String ouid = "testOuid";
        final int level = 1;
        final int page = 0;
        final int pageSize = 10;
        final int typeNumber = MatchTypeData.OFFICIAL.getNumber();
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Member member = new Member(nickname, ouid, level, LocalDateTime.now());
        given(nexonApiClient.requestUserOuid(nickname)).willReturn(ouid);
        given(memberRepository.findByAccessId(ouid)).willReturn(Optional.of(member));
        // ArgumentCaptor를 사용하여 findAllMatches 메소드에 전달된 matchTypes 인자를 포착
        ArgumentCaptor<List<Integer>> matchTypesCaptor = ArgumentCaptor.forClass(List.class);

        given(matchRepository.findAllMatches(matchTypesCaptor.capture(), eq(ouid), anyString(), any(Pageable.class)))
                .willReturn(new PageImpl<>(matches.stream()
                        .filter(o -> o.getMatchType() == typeNumber)
                        .collect(Collectors.toList()))
                );

        //when
        RecentMatchDto result = matchService.getRecentMatch(nickname, page, typeNumber);

        //then
        List<Integer> capturedMatchTypes = matchTypesCaptor.getValue();
        assertTrue(capturedMatchTypes.containsAll(determineMatchTypes(typeNumber)));
        assertEquals(1, result.getMatchResponse().size());
    }

    @DisplayName("최근 전적 불러올 때 존재하지 않는 멤버 조회시 예외 발생")
    @Test
    void getRecentMatchShouldThrowMemberNotFoundException() {
        //given
        final String nickname = "testNickname";
        final String ouid = "testOuid";
        final int page = 0;
        final int typeNumber = MatchTypeData.OFFICIAL.getNumber();

        given(nexonApiClient.requestUserOuid(nickname)).willReturn(ouid);
        given(memberRepository.findByAccessId(ouid)).willThrow(MemberNotFoundException.class);

        //when & then
        assertThrows(MemberNotFoundException.class, () -> matchService.getRecentMatch(nickname, page, typeNumber));
    }



    @DisplayName("상대 전적 불러오기할 시 Mapping이 잘되서 반환되는 지 확인")
    @Test
    void getRelativeMatch() {
        //given
        final String nickname = "PlayerTwo";
        final String ouid = "player2";
        final String opponentNickname = "PlayerOne";
        final int level = 1;
        final int page = 0;
        final int pageSize = 10;
        final int matchLossResult = 1;
        final LocalDateTime renewal =LocalDateTime.now();
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Member member = new Member(nickname, ouid, level, renewal);
        given(nexonApiClient.requestUserOuid(nickname)).willReturn(ouid);
        given(memberRepository.findByAccessId(ouid)).willReturn(Optional.of(member));
        given(matchRepository.findAllMatches(anyList(), anyString(), anyString(), any(Pageable.class)))
                .willReturn(new PageImpl<>(matches.stream()
                        .filter(o -> o.getTeam1().contains(ouid) || o.getTeam2().contains(ouid))
                        .collect(Collectors.toList()))
                );
        //when
        RelativeMatchDto result = matchService.getRelativeMatch(nickname);

        //then

        assertEquals(1, result.getRelativeMatchResponse().size());

        assertEquals(result.getMemberInfo().getNickname(), nickname);
        assertEquals(result.getMemberInfo().getOuid(), ouid);
        assertEquals(result.getMemberInfo().getLevel(), level);
        assertEquals(result.getMemberInfo().getRenewal(), renewal);

        assertEquals(matchLossResult, result.getRelativeMatchResponse().stream()
                .filter(o -> o.getWin() >= 0)
                .count()
        );
        assertEquals(opponentNickname, result.getRelativeMatchResponse().stream()
                .map(o -> o.getOpponentNickname())
                .findFirst()
                .get());
    }



    @DisplayName("상대 전적 불러올 때 존재하지 않는 멤버 조회시 예외 발생")
    @Test
    void getRelativeMatchShouldThrowMemberNotFoundException() {
        //given
        final String nickname = "testNickname";
        final String ouid = "testOuid";

        given(nexonApiClient.requestUserOuid(nickname)).willReturn(ouid);
        given(memberRepository.findByAccessId(ouid)).willThrow(MemberNotFoundException.class);

        //when & then
        assertThrows(MemberNotFoundException.class, () -> matchService.getRelativeMatch(nickname));

    }



}
