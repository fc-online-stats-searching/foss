package com.foss.foss.model

import com.foss.foss.model.legacy.WinDrawLose

object MatchMapper {

    fun Match.toUiModel() = MatchUiModel(
        date = date,
        manOfTheMatch = "https://fco.dn.nexoncdn.co.kr/live/externalAssets/common/players/p$manOfTheMatch.png",
        matchType = matchType.toUiModel(),
        otherSideNickname = otherSideNickname,
        winDrawLose = winDrawLose.toUiModel(),
        point = score.point,
        otherPoint = score.otherPoint
    )

    fun MatchType.toUiModel() = when (this) {
        MatchType.LEAGUE_FRIENDLY -> "리그 친선"
        MatchType.CLASSIC_ONE_TO_ONE -> "클래식 1on1"
        MatchType.OFFICIAL -> "공식 경기"
        MatchType.DIRECTOR -> "감독 모드"
        MatchType.OFFICIAL_FRIENDLY -> "공식 친선"
        MatchType.VOLTA_FRIENDLY -> "볼타 친선"
        MatchType.VOLTA_OFFICIAL -> "볼타 공식 경기"
        MatchType.VOLTA_CUSTOM -> "볼타 커스텀"
    }

    private fun WinDrawLose.toUiModel() = when (this) {
        WinDrawLose.WIN -> "승"
        WinDrawLose.DRAW -> "무"
        WinDrawLose.LOSE -> "패"
    }
}
