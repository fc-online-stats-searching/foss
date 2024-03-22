package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("level")
    val level: Int,
    @SerialName("renewal")
    val renewal: String,
    @SerialName("ouid")
    val ouid: String
)
