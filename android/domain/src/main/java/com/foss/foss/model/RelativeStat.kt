package com.foss.foss.model

import java.time.LocalDate

/**
 * todo : opponentName으로 통일하기
 */
data class RelativeStat(
    val opponentName: Nickname,
    val recentMatchDate: LocalDate,
    val winDrawLoses: WinDrawLoses,
    val totalScore: Score,
)
