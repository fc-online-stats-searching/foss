package com.foss.foss.model

import com.foss.foss.model.legacy.Score
import java.time.LocalDate

data class Match(
    val date: LocalDate,
    val matchType: MatchType,
    val manOfTheMatch: Int,
    val otherSideNickname: String,
    val winDrawLose: WinDrawLose,
    val score: Score
)
