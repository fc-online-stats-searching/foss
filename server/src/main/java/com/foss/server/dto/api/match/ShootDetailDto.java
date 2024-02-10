package com.foss.server.dto.api.match;

import lombok.Data;

@Data
public class ShootDetailDto {

    private int goalTime;
    private double x;
    private double y;
    private int type;
    private int result;
    private int spId;
    private int spGrade;
    private int spLevel;
    private boolean spIdType;
    private boolean assist;
    private int assistSpI;
    private double assistX;
    private double assistY;
    private boolean hitPost;
    private boolean inPenalty;

}

