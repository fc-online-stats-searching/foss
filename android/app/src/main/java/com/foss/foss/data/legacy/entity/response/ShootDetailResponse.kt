package com.foss.foss.data.legacy.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShootDetailResponse(
    @SerialName("goalTime")
    val goalTime: Int,
    @SerialName("x")
    val x: Double,
    @SerialName("y")
    val y: Double,
    @SerialName("type")
    val type: Int,
    @SerialName("result")
    val result: Int,
    @SerialName("spId")
    val spId: Int,
    @SerialName("spGrade")
    val spGrade: Int,
    @SerialName("spLevel")
    val spLevel: Int,
    @SerialName("spIdType")
    val spIdType: Boolean,
    @SerialName("assist")
    val assist: Boolean,
    @SerialName("assistSpId")
    val assistSpId: Int,
    @SerialName("assistX")
    val assistX: Double,
    @SerialName("assistY")
    val assistY: Double,
    @SerialName("hitPost")
    val hitPost: Boolean,
    @SerialName("inPenalty")
    val inPenalty: Boolean
)
