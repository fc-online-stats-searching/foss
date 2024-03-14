package com.foss.server.api.dto.match;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OuIdResponseDto {

    @JsonProperty("ouid")
    private String ouid;
}
