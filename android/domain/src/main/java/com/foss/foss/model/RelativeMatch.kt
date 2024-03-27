package com.foss.foss.model

import java.time.LocalDate

data class RelativeMatch(
    val opponentName: String,
    val opponentDivision: Division,
    val recentMatchDate: LocalDate?,
    val winDrawLoses: WinDrawLoses,
    val totalScore: Score,
    val matchDetails: List<Match>
)
