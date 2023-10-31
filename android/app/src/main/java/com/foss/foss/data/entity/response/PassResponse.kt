package com.foss.foss.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PassResponse(
    @SerialName("passTry")
    val passTry: Int,
    @SerialName("passSuccess")
    val passSuccess: Int,
    @SerialName("shortPassTry")
    val shortPassTry: Int,
    @SerialName("shortPassSuccess")
    val shortPassSuccess: Int,
    @SerialName("longPassTry")
    val longPassTry: Int,
    @SerialName("longPassSuccess")
    val longPassSuccess: Int,
    @SerialName("bouncingLobPassTry")
    val bouncingLobPassTry: Int,
    @SerialName("bouncingLobPassSuccess")
    val bouncingLobPassSuccess: Int,
    @SerialName("drivenGroundPassTry")
    val drivenGroundPassTry: Int,
    @SerialName("drivenGroundPassSuccess")
    val drivenGroundPassSuccess: Int,
    @SerialName("throughPassTry")
    val throughPassTry: Int,
    @SerialName("throughPassSuccess")
    val throughPassSuccess: Int,
    @SerialName("lobbedThroughPassTry")
    val lobbedThroughPassTry: Int,
    @SerialName("lobbedThroughPassSuccess")
    val lobbedThroughPassSuccess: Int
)
