package com.foss.foss.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchInfoResponse(
    @SerialName("accessId")
    val accessId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("matchDetail")
    val matchDetailResponse: MatchDetailResponse,
    @SerialName("shoot")
    val shootResponse: ShootResponse,
    @SerialName("shootDetail")
    val shootDetailResponse: List<ShootDetailResponse>,
    @SerialName("pass")
    val passResponse: PassResponse,
    @SerialName("defence")
    val defenceResponse: DefenceResponse,
    @SerialName("player")
    val playerResponse: List<PlayerResponse>
)
