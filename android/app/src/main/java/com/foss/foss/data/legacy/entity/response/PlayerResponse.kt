package com.foss.foss.data.legacy.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerResponse(
    @SerialName("spId")
    val spId: Int,
    @SerialName("spPosition")
    val spPosition: Int,
    @SerialName("spGrade")
    val spGrade: Int,
    @SerialName("status")
    val status: StatusResponse
)
