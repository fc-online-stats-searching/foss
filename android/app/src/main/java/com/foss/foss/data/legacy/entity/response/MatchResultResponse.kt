package com.foss.foss.data.legacy.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchResultResponse(
    @SerialName("matchId")
    val matchId: String,
    @SerialName("matchDate")
    val matchDate: String,
    @SerialName("matchType")
    val matchType: Int,
    @SerialName("matchInfo")
    val matchesInfoResponse: List<MatchInfoResponse>
)
