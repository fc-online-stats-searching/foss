package com.foss.foss.model

import java.time.LocalDate

data class RelativeStatUiModel(
    val opponentName: String,
    val numberOfGames: Int,
    val numberOfWins: Int,
    val numberOfDraws: Int,
    val numberOfLoses: Int,
    val recentMatchDate: LocalDate,
    val goal: Int,
    val conceded: Int
)
