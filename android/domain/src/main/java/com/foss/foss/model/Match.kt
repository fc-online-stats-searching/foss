package com.foss.foss.model

import java.time.LocalDateTime

data class Match(
    val date: LocalDateTime,
    val matchType: MatchType,
    val manOfTheMatch: Int?,
    val opponentName: String,
    val winDrawLose: WinDrawLose,
    val score: Score,
)
