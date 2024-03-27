package com.foss.foss.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class MatchUiModel(
    val date: LocalDateTime,
    val matchType: MatchTypeUiModel,
    val manOfTheMatch: Int?,
    val opponentName: String,
    val opponentDivision: DivisionUiModel,
    val winDrawLose: WinDrawLoseUiModel,
    val point: Int,
    val otherPoint: Int
) : Parcelable
