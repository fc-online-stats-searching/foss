package com.foss.foss.model

import java.time.LocalDate

/**
 * 날짜별로 경기 기록을 모으기 위한 data class
 */
data class MatchesUiModel(
    val date: LocalDate,
    val value: List<MatchUiModel>
)
