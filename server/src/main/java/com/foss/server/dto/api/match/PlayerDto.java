package com.foss.server.dto.api.match;

import lombok.Data;

@Data
public class PlayerDto {

    private int spId;
    private int spPosition;
    private int spGrade;
    private PlayerStatusDto status;

}