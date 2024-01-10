package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchDto(
    @SerialName("matchType")
    val matchType: String,
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("result")
    val result: String,
    @SerialName("nickName")
    val nickName: String,
    @SerialName("opponentNickname")
    val opponentNickname: String,
    @SerialName("matchDetail")
    val matchDetail: MatchDetailDto,
)
