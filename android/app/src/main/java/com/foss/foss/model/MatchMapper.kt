package com.foss.foss.model

object MatchMapper {

    fun Match.toUiModel() = MatchUiModel(
        date = date,
        manOfTheMatch = "https://fco.dn.nexoncdn.co.kr/live/externalAssets/common/players/p$manOfTheMatch.png",
        matchType = matchType.toUiModel(),
        opponentName = opponentName,
        winDrawLose = winDrawLose.toUiModel(),
        point = score.point,
        otherPoint = score.otherPoint
    )

    fun MatchType.toUiModel() = when (this) {
        MatchType.CLASSIC_ONE_TO_ONE -> MatchTypeUiModel.CLASSIC_ONE_TO_ONE
        MatchType.OFFICIAL -> MatchTypeUiModel.OFFICIAL
        MatchType.ALL -> MatchTypeUiModel.ALL
    }

    fun MatchTypeUiModel.toDomainModel() = when (this) {
        MatchTypeUiModel.CLASSIC_ONE_TO_ONE -> MatchType.CLASSIC_ONE_TO_ONE
        MatchTypeUiModel.OFFICIAL -> MatchType.OFFICIAL
        MatchTypeUiModel.ALL -> MatchType.ALL
    }

    fun WinDrawLose.toUiModel() = when (this) {
        WinDrawLose.WIN -> WinDrawLoseUiModel.WIN
        WinDrawLose.DRAW -> WinDrawLoseUiModel.DRAW
        WinDrawLose.LOSE -> WinDrawLoseUiModel.LOSE
    }

    fun MatchType.toIntType() = when (this) {
        MatchType.OFFICIAL -> 50
        MatchType.CLASSIC_ONE_TO_ONE -> 40
        MatchType.ALL -> 10
        else -> 10
    }
}
