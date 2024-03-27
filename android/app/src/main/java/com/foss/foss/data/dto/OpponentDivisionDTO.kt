package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpponentDivisionDTO(
    @SerialName("matchType")
    val matchType: Int,
    @SerialName("division")
    val division: Int,
    @SerialName("achievementDate")
    val achievementDate: String?
)
