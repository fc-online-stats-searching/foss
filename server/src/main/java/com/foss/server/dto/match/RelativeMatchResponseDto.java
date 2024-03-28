package com.foss.server.dto.match;

import com.foss.server.dto.match.MatchResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelativeMatchResponseDto{
    private LocalDateTime lastDate;
    private String opponentNickname;
    private int win;
    private int tie;
    private int lose;
    private int gain;
    private int loss;
    private List<MatchResponseDto> matchResponse;
}
