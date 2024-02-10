package com.foss.server.dto.api.match;

import lombok.Data;

@Data
public class ShootDto {

    private int shootTotal;
    private int effectiveShootTotal;
    private int shootOutScore;
    private int goalTotal;
    private int goalTotalDisplay;
    private int ownGoal;
    private int shootHeading;
    private int goalHeading;
    private int shootFreekick;
    private int goalFreekick;
    private int shootInPenalty;
    private int goalInPenalty;
    private int shootOutPenalty;
    private int goalOutPenalty;
    private int shootPenaltyKick;
    private int goalPenaltyKick;

}