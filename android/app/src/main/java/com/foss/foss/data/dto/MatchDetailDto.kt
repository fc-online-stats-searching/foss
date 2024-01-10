package com.foss.foss.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MatchDetailDto(
    val possession: Int,
    val shootCount: Int,
    val effectiveShootCount: Int,
    val passCount: Int,
    val passSuccessRate: Int,
    val squads: List<SquadsDto>,
)
