package com.foss.foss.util

import com.foss.foss.model.RelativeMatchUiModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun calculateRecentMatchTime(relativeMatchUiModel: RelativeMatchUiModel): String? {
    val matchDetails = relativeMatchUiModel.matchDetails
    if (matchDetails.isEmpty()) {
        return null
    }

    val recentMatchTime = matchDetails.maxByOrNull { it.date }?.date
    val currentTime = LocalDateTime.now()

    if (recentMatchTime == null) {
        return null
    }

    val difference = ChronoUnit.MINUTES.between(recentMatchTime, currentTime)

    val days = difference / (60 * 24)
    val hours = (difference % (60 * 24)) / 60
    val minutes = difference % 60

    return when {
        days > 1 -> "${days}일 ${hours}시간 ${minutes}분 전"
        days == 1L -> "1일 ${hours}시간 ${minutes}분 전"
        hours > 1 -> "${hours}시간 ${minutes}분 전"
        hours == 1L -> "1시간 ${minutes}분 전"
        else -> "${minutes}분 전"
    }
}
