package com.foss.server.dto.api.user;

import com.foss.server.domain.member.Member;
import lombok.Getter;

@Getter
public class UserApiResponseDto {

    private String ouid;
    private String nickname;
    private int level;

}
