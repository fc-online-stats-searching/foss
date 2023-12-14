package com.foss.foss.model

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
        MatchType.LEAGUE_FRIENDLY -> MatchTypeUiModel.LEAGUE_FRIENDLY
        MatchType.CLASSIC_ONE_TO_ONE -> MatchTypeUiModel.CLASSIC_ONE_TO_ONE
        MatchType.OFFICIAL -> MatchTypeUiModel.OFFICIAL
        MatchType.DIRECTOR -> MatchTypeUiModel.DIRECTOR
        MatchType.OFFICIAL_FRIENDLY -> MatchTypeUiModel.OFFICIAL_FRIENDLY
        MatchType.VOLTA_FRIENDLY -> MatchTypeUiModel.VOLTA_FRIENDLY
        MatchType.VOLTA_OFFICIAL -> MatchTypeUiModel.VOLTA_OFFICIAL
        MatchType.VOLTA_CUSTOM -> MatchTypeUiModel.VOLTA_CUSTOM
    }

    fun WinDrawLose.toUiModel() = when (this) {
        WinDrawLose.WIN -> WinDrawLoseUiModel.WIN
        WinDrawLose.DRAW -> WinDrawLoseUiModel.DRAW
        WinDrawLose.LOSE -> WinDrawLoseUiModel.LOSE
    }
}
