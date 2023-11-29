package com.foss.foss.data.legacy.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchRequest(
    @SerialName("matchId")
    val matchId: String
)
