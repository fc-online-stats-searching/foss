package com.foss.server.api.dto.match;

import lombok.Data;

@Data
public class DefenceDto {

    private int blockTry;
    private int blockSuccess;
    private int tackleTry;
    private int tackleSuccess;

}