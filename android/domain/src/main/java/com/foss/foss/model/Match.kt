package com.foss.foss.model

import java.time.LocalDate

data class Match(
    val date: LocalDate,
    val matchType: MatchType,
    val manOfTheMatch: Int,
    val otherSideNickname: Nickname,
    val winDrawLose: WinDrawLose,
    val score: Score,
)
