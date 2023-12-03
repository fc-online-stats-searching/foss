package com.foss.foss.model

import java.time.LocalDate

data class MatchUiModel(
    val date: LocalDate,
    val matchType: MatchTypeUiModel,
    val manOfTheMatch: String,
    val otherSideNickname: String,
    val winDrawLose: WinDrawLoseUiModel,
    val point: Int,
    val otherPoint: Int
)
