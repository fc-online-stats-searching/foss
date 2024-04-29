package com.foss.server.controller;

import com.foss.server.api.NexonApiClient;
import com.foss.server.api.dto.user.UserApiResponseDto;
import com.foss.server.domain.event.Event;
import com.foss.server.service.EventService;
import com.foss.server.service.MatchService;
import com.foss.server.service.MemberService;
import com.foss.server.dto.member.MemberInfoResponseDto;
import com.foss.server.dto.match.RecentMatchDto;
import com.foss.server.dto.RefreshDto;
import com.foss.server.dto.match.RelativeMatchDto;
import com.foss.server.service.SyncMatchTestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Tag(name = "FOSS Controller", description = "FOSS 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final MatchService matchService;
    private final NexonApiClient nexonApiClient;
    private final EventService eventService;

    @PostMapping("/refresh")
    public ResponseEntity<MemberInfoResponseDto> refresh(
            @RequestBody RefreshDto refreshDto
    ) {
        UserApiResponseDto userApiResponseDto = matchService.refreshMatchListWebClient(refreshDto.getNickname());
        MemberInfoResponseDto memberInfoResponseDto = memberService.refreshMemberByUserApiResponse(userApiResponseDto);
        return ResponseEntity.ok(memberInfoResponseDto);
    }

    @PostMapping("/refresh/sync")
    public ResponseEntity<MemberInfoResponseDto> refreshSync(
            @RequestBody RefreshDto refreshDto
    ) {
        String ouid = nexonApiClient.requestUserOuid(refreshDto.getNickname());

        CompletableFuture<MemberInfoResponseDto> memberInfoFuture = CompletableFuture.supplyAsync(() ->
                memberService.refreshMember(ouid)
        );

        CompletableFuture<Void> matchListFuture = CompletableFuture.runAsync(() ->
                matchService.refreshMatchList(ouid)
        );

        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(memberInfoFuture, matchListFuture);
        allOfFuture.join();

        return ResponseEntity.ok(memberInfoFuture.join());
    }

    @GetMapping("/matches")
    public ResponseEntity<RecentMatchDto> getRecentMatches(@RequestParam int page, @RequestParam String nickname, @RequestParam int matchType) {
        return ResponseEntity.ok(matchService.getRecentMatch(nickname, page, matchType));
    }

    @GetMapping("/matches/relative")
    public ResponseEntity<RelativeMatchDto> getRelativeMatches(@RequestParam String nickname) {
        return ResponseEntity.ok(matchService.getRelativeMatch(nickname));
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEventList(@RequestParam(name = "limitPage", defaultValue = "3", required = false) int limitPage) {
        return ResponseEntity.ok(eventService.getEvents(limitPage));
    }

}
