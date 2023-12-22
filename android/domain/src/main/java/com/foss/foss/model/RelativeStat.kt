package com.foss.foss.model

import com.foss.foss.model.legacy.Score
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
