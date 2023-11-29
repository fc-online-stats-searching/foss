package com.foss.foss.model.legacy

data class MatchResult(
    val matchType: MatchType,
    val otherSideNickname: String,
    val winDrawLose: WinDrawLose,
    val score: Score
)
