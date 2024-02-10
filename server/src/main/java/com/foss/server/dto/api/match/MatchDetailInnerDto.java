package com.foss.server.dto.api.match;

import lombok.Data;

@Data
public class MatchDetailInnerDto {

    private int seasonId;
    private String matchResult;
    private int matchEndType;
    private int systemPause;
    private int foul;
    private int injury;
    private int redCards;
    private int yellowCards;
    private int dribble;
    private int cornerKick;
    private int possession;
    private int OffsideCount;
    private double averageRating;
    private String controller;

}