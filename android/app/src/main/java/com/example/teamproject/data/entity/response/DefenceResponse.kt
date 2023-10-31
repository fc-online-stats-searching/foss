package com.example.teamproject.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DefenceResponse(
    @SerialName("blockTry")
    val blockTry: Int,
    @SerialName("blockSuccess")
    val blockSuccess: Int,
    @SerialName("tackleTry")
    val tackleTry: Int,
    @SerialName("tackleSuccess")
    val tackleSuccess: Int
)
