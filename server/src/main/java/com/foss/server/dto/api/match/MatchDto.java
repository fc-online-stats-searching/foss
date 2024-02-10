package com.foss.server.dto.api.match;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchDto {

    private String matchId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime matchDate;

    private int matchType;

    private List<MatchDetailDto> matchInfo;

}