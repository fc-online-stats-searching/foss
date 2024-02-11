package com.foss.server.controller;

import com.foss.server.api.NexonApiClient;
import com.foss.server.dto.api.user.UserApiResponseDto;
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

@Tag(name = "FOSS Controller", description = "FOSS 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final MatchService matchService;

    @PostMapping("/refresh")
    public ResponseEntity<MemberInfoResponseDto> refresh(
            @RequestBody RefreshDto refreshDto
    ) {
        MemberInfoResponseDto memberInfo = memberService.refreshMember(refreshDto.getNickname());
        matchService.refreshMatchList(memberInfo.getOuid());

        return ResponseEntity.ok(memberInfo);
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
