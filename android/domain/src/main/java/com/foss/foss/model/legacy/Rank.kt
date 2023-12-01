package com.foss.foss.model.legacy

import com.foss.foss.model.MatchType

data class Rank(
    val matchType: MatchType,
    val division: Int,
    val achievementDate: String
)
