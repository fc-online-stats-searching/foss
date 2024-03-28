package com.foss.server.api.dto.match;

import lombok.Data;

@Data
public class PlayerDto {

    private int spId;
    private int spPosition;
    private int spGrade;
    private PlayerStatusDto status;

}