package com.foss.server.dto.api.match;

import lombok.Data;

@Data
public class PlayerStatusDto {

    private int shoot;
    private int effectiveShoot;
    private int assist;
    private int goal;
    private int dribble;
    private int intercept;
    private int defending;
    private int passTry;
    private int passSuccess;
    private int dribbleTry;
    private int dribbleSuccess;
    private int ballPossessionTry;
    private int ballPossessionSuccess;
    private int aerialTry;
    private int aerialSuccess;
    private int blockTry;
    private int block;
    private int tackleTry;
    private int tackle;
    private int yellowCards;
    private int redCards;
    private double spRating;

}