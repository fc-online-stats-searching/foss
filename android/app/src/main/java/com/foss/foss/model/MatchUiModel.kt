package com.foss.foss.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MatchUiModel(
    val date: LocalDate,
    val matchType: MatchTypeUiModel,
    val manOfTheMatch: String?,
    val opponentName: String,
    val winDrawLose: WinDrawLoseUiModel,
    val point: Int,
    val otherPoint: Int
) : Parcelable
