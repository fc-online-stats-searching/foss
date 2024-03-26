package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FcEventDTO(
    @SerialName("link")
    val nexonURL: String,
    @SerialName("image")
    val imageURL: String,
    @SerialName("text")
    val description: String
)
