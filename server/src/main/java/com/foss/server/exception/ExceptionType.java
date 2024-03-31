package com.foss.server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    NOT_FOUND_MEMBER_ID(5000, "Member Not Found Exception"),
    NOT_FOUND_MEMBER_NICKNAME(5001, "Nickname Not Found Exception"),
    NOT_FOUND_MEMBER_DIVISION(5002, "Member Division Not Found Exception"),
    NOT_FOUND_MATCH_ID(5002, "MatchId Not Found Exception"),
    MANY_REQUEST_API(6000, "최대 재시도 횟수에 도달했습니다. 매치 정보를 가져올 수 없습니다."),
    MISSING_REQUEST_PARAMETER(400,"매개 변수가 누락되었습니다.");



    private int code;
    private String message;
}
