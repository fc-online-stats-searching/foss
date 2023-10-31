package com.foss.foss.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    @SerialName("shoot")
    val shoot: Int,
    @SerialName("effectiveShoot")
    val effectiveShoot: Int,
    @SerialName("assist")
    val assist: Int,
    @SerialName("goal")
    val goal: Int,
    @SerialName("dribble")
    val dribble: Int,
    @SerialName("intercept")
    val intercept: Int,
    @SerialName("defending")
    val defending: Int,
    @SerialName("passTry")
    val passTry: Int,
    @SerialName("passSuccess")
    val passSuccess: Int,
    @SerialName("dribbleTry")
    val dribbleTry: Int,
    @SerialName("dribbleSuccess")
    val dribbleSuccess: Int,
    @SerialName("ballPossesionTry")
    val ballPossesionTry: Int,
    @SerialName("ballPossesionSuccess")
    val ballPossesionSuccess: Int,
    @SerialName("aerialTry")
    val aerialTry: Int,
    @SerialName("aerialSuccess")
    val aerialSuccess: Int,
    @SerialName("blockTry")
    val blockTry: Int,
    @SerialName("block")
    val block: Int,
    @SerialName("tackleTry")
    val tackleTry: Int,
    @SerialName("tackle")
    val tackle: Int,
    @SerialName("yellowCards")
    val yellowCards: Int,
    @SerialName("redCards")
    val redCards: Int,
    @SerialName("spRating")
    val spRating: Double
)
