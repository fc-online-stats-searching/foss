package com.foss.server.controller;

import com.foss.server.api.NexonApiClient;
import com.foss.server.service.MatchService;
import com.foss.server.service.MemberService;
import com.foss.server.dto.member.MemberInfoResponseDto;
import com.foss.server.dto.match.RecentMatchDto;
import com.foss.server.dto.RefreshDto;
import com.foss.server.dto.match.RelativeMatchDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Tag(name = "FOSS Controller", description = "FOSS 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final MatchService matchService;
    private final NexonApiClient nexonApiClient;

    @PostMapping("/refresh")
    public ResponseEntity<MemberInfoResponseDto> refresh(
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
    public ResponseEntity<RecentMatchDto> getRecentMatches(@RequestParam int page, @RequestParam String nickname,@RequestParam int matchType) {
        return ResponseEntity.ok(matchService.getRecentMatch(nickname, page, matchType));
    }

    @GetMapping("/matches/relative")
    public ResponseEntity<RelativeMatchDto> getRelativeMatches(@RequestParam String nickname) {
        return ResponseEntity.ok(matchService.getRelativeMatch(nickname));
    }

}
