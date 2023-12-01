package com.foss.foss.model

import java.time.LocalDate

data class MatchUiModel(
    val date: LocalDate,
    val matchType: String,
    val manOfTheMatch: String,
    val otherSideNickname: String,
    val winDrawLose: String,
    val point: Int,
    val otherPoint: Int
)
