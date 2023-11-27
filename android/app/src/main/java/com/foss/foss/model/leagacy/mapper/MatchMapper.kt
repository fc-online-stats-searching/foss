package com.foss.foss.model.leagacy.mapper

import com.foss.foss.model.legacy.MatchResult
import com.foss.foss.model.legacy.MatchType
import com.foss.foss.model.legacy.WinDrawLose

object MatchMapper {

    fun MatchResult.toUiModel() = MatchResultUiModel(
        matchType = matchType.toUiModel(),
        otherSideNickname = otherSideNickname,
        winDrawLose = winDrawLose.toUiModel(),
        point = score.point,
        otherPoint = score.otherPoint
    )

    private fun MatchType.toUiModel() = when (this) {
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
