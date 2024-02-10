package com.foss.server.dto.api.match;

import lombok.Data;

import java.util.List;
@Data
public class MatchDetailDto {

    private String ouid;
    private String nickname;
    private MatchDetailInnerDto matchDetail;
    private ShootDto shoot;
    private List<ShootDetailDto> shootDetail;
    private PassDto pass;
    private DefenceDto defence;
    private List<PlayerDto> player;

}