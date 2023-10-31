package com.example.teamproject.data.entity.response

import kotlinx.serialization.SerialName

data class RankResponse(
    @SerialName("matchType")
    val matchType: Int,
    @SerialName("division")
    val division: Int,
    @SerialName("achievementDate")
    val achievementDate: String
)
