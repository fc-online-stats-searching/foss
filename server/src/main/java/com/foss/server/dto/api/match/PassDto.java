package com.foss.server.dto.api.match;

import lombok.Data;

@Data
public class PassDto {

    private int passTry;
    private int passSuccess;
    private int shortPassTry;
    private int shortPassSuccess;
    private int longPassTry;
    private int longPassSuccess;
    private int bouncingLobPassTry;
    private int bouncingLobPassSuccess;
    private int drivenGroundPassTry;
    private int drivenGroundPassSuccess;
    private int throughPassTry;
    private int throughPassSuccess;
    private int lobbedThroughPassTry;
    private int lobbedThroughPassSuccess;

}