package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelativeMatchDTO(
    @SerialName("lastDate")
    val lastDate: String,
    @SerialName("opponentNickname")
    val opponentNickname: String,
    @SerialName("win")
    val win: Int,
    @SerialName("tie")
    val tie: Int,
    @SerialName("lose")
    val lose: Int,
    @SerialName("gain")
    val gain: Int,
    @SerialName("loss")
    val loss: Int,
    @SerialName("matchResponse")
    val matchResponse: List<MatchDto>
)
