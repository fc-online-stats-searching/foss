package com.foss.server.dto.api.match;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OuIdResponseDto {

    @JsonProperty("ouid")
    private String ouid;
}
