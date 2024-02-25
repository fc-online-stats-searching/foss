package com.foss.server.domain.metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchTypeData {
    ALL("전체", 10),
    LEAGUE_FRIEND("리그 친선", 30),
    CLASSIC_1ON1("클래식 1on1", 40),
    OFFICIAL("공식 경기", 50),
    SUPERVISOR("감독 모드", 52),
    OFFICIAL_FRIEND("공식 친선", 60),
    BOLTA_FRIEND("볼타 친선", 204),
    BOLTA_OFFICIAL("볼타 공식", 214),
    BOLTA_AI("볼타 AI대전", 224),
    BOLTA_CUSTOM("볼타 커스텀", 234);

    private String name;
    private int number;

}