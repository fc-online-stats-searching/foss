package com.foss.server.dto.member;

import com.foss.server.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MemberInfoResponseDto {

    private String nickname;
    private int level;
    private LocalDateTime renewal;
    private String ouid;

    public static MemberInfoResponseDto from(Member member) {
        return new MemberInfoResponseDto(
                member.getUsername(),
                member.getLevel(),
                member.getRenewal(),
                member.getAccessId()
        );
    }

}
