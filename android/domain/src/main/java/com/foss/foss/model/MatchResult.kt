package com.foss.foss.model

data class MatchResult(
    val matchType: MatchType,
    val otherSideNickname: String,
    val winDrawLose: WinDrawLose,
    val score: Score
)
