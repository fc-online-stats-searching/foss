package com.foss.server.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserApiResponseDto {

    private String ouid;
    private String nickname;
    private int level;

}
