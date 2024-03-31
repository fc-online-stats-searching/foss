package com.foss.server.api.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDivisionDto {
    private int matchType;
    private int division;
    private String achievementDate;
}
