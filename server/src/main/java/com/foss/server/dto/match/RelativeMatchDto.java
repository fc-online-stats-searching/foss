package com.foss.server.dto.match;

import com.foss.server.dto.member.MemberInfoResponseDto;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelativeMatchDto {
    private MemberInfoResponseDto memberInfo;
    private List<RelativeMatchResponseDto> relativeMatchResponse;
}
