package com.foss.foss.data.legacy.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchDetailResponse(
    @SerialName("seasonId")
    val seasonId: Int,
    @SerialName("matchResult")
    val matchResult: String,
    @SerialName("matchEndType")
    val matchEndType: Int,
    @SerialName("systemPause")
    val systemPause: Int,
    @SerialName("foul")
    val foul: Int,
    @SerialName("injury")
    val injury: Int,
    @SerialName("redCards")
    val redCards: Int,
    @SerialName("yellowCards")
    val yellowCards: Int,
    @SerialName("dribble")
    val dribble: Int,
    @SerialName("cornerKick")
    val cornerKick: Int,
    @SerialName("possession")
    val possession: Int,
    @SerialName("offsideCount")
    val offsideCount: Int,
    @SerialName("averageRating")
    val averageRating: Double,
    @SerialName("controller")
    val controller: String
)
