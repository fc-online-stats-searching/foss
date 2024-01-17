package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchDetailDto(
    @SerialName("possession")
    val possession: Int,
    @SerialName("shootCount")
    val shootCount: Int,
    @SerialName("effectiveShootCount")
    val effectiveShootCount: Int,
    @SerialName("passCount")
    val passCount: Int,
    @SerialName("passSuccessRate")
    val passSuccessRate: Int,
    @SerialName("squads")
    val squads: List<SquadsDto>,
)
