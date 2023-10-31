package com.example.teamproject.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShootResponse(
    @SerialName("shootTotal")
    val shootTotal: Int,
    @SerialName("effectiveShootTotal")
    val effectiveShootTotal: Int,
    @SerialName("shootOutScore")
    val shootOutScore: Int,
    @SerialName("goalTotal")
    val goalTotal: Int,
    @SerialName("goalTotalDisplay")
    val goalTotalDisplay: Int,
    @SerialName("ownGoal")
    val ownGoal: Int,
    @SerialName("shootHeading")
    val shootHeading: Int,
    @SerialName("goalHeading")
    val goalHeading: Int,
    @SerialName("shootFreekick")
    val shootFreekick: Int,
    @SerialName("goalFreekick")
    val goalFreekick: Int,
    @SerialName("shootInPenalty")
    val shootInPenalty: Int,
    @SerialName("goalInPenalty")
    val goalInPenalty: Int,
    @SerialName("shootOutPenalty")
    val shootOutPenalty: Int,
    @SerialName("goalOutPenalty")
    val goalOutPenalty: Int,
    @SerialName("shootPenaltyKick")
    val shootPenaltyKick: Int,
    @SerialName("goalPenaltyKick")
    val goalPenaltyKick: Int
)
