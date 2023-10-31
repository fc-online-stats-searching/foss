package com.example.teamproject.data.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("accessId")
    val accessId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("level")
    val level: Int
)
