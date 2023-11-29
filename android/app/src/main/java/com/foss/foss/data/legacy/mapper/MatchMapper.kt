package com.foss.foss.data.legacy.mapper

import com.foss.foss.model.legacy.MatchType
import com.foss.foss.model.legacy.WinDrawLose

class MatchMapper {

    fun mapToDomain(winDrawLose: String): WinDrawLose = when (winDrawLose) {
        WIN -> WinDrawLose.WIN
        DRAW -> WinDrawLose.DRAW
        LOSE -> WinDrawLose.LOSE
        else -> throw NoSuchElementException(NO_SUCH_WIN_DRAW_LOSE)
    }

    fun mapToDomain(matchType: Int): MatchType = when (matchType) {
        LEAGUE_FRIENDLY -> MatchType.LEAGUE_FRIENDLY
        CLASSIC_ONE_TO_ONE -> MatchType.CLASSIC_ONE_TO_ONE
        OFFICIAL -> MatchType.OFFICIAL
        DIRECTOR -> MatchType.DIRECTOR
        OFFICIAL_FRIENDLY -> MatchType.OFFICIAL_FRIENDLY
        VOLTA_FRIENDLY -> MatchType.VOLTA_FRIENDLY
        VOLTA_OFFICIAL -> MatchType.VOLTA_OFFICIAL
        VOLTA_CUSTOM -> MatchType.VOLTA_CUSTOM
        else -> throw NoSuchElementException(NO_SUCH_MATCH_TYPE)
    }

    fun mapToEntity(matchType: MatchType): Int = when (matchType) {
        MatchType.LEAGUE_FRIENDLY -> LEAGUE_FRIENDLY
        MatchType.CLASSIC_ONE_TO_ONE -> CLASSIC_ONE_TO_ONE
        MatchType.OFFICIAL -> OFFICIAL
        MatchType.DIRECTOR -> DIRECTOR
        MatchType.OFFICIAL_FRIENDLY -> OFFICIAL_FRIENDLY
        MatchType.VOLTA_FRIENDLY -> VOLTA_FRIENDLY
        MatchType.VOLTA_OFFICIAL -> VOLTA_OFFICIAL
        MatchType.VOLTA_CUSTOM -> VOLTA_CUSTOM
        else -> throw NoSuchElementException(NO_SUCH_MATCH_TYPE)
    }

    companion object {
        private const val LEAGUE_FRIENDLY = 30
        private const val CLASSIC_ONE_TO_ONE = 40
        private const val OFFICIAL = 50
        private const val DIRECTOR = 52
        private const val OFFICIAL_FRIENDLY = 60
        private const val VOLTA_FRIENDLY = 204
        private const val VOLTA_OFFICIAL = 224
        private const val VOLTA_CUSTOM = 234
        private const val NO_SUCH_MATCH_TYPE = "알 수 없는 경기 타입입니다."

        private const val WIN = "승"
        private const val DRAW = "무"
        private const val LOSE = "패"
        private const val NO_SUCH_WIN_DRAW_LOSE = "알 수 없는 경기 결과입니다."
    }
}
