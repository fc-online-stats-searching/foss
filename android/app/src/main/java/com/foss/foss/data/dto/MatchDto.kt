package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchDto(
    @SerialName("matchType")
    val matchType: Int,
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("result")
    val result: String,
    @SerialName("goals")
    val goals: Map<String, Int>,
    @SerialName("nickName")
    val nickName: String,
    @SerialName("opponentNickname")
    val opponentNickname: String,
    @SerialName("opponentDivision")
    val opponentDivision: OpponentDivisionDTO,
    @SerialName("matchDetail")
    val matchDetail: MatchDetailDto
)
