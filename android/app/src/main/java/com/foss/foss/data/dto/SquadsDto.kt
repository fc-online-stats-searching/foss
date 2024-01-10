package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SquadsDto(
    @SerialName("pid")
    val pid: Int,
    @SerialName("position")
    val position: Int,
    @SerialName("grade")
    val grade: Int,
)
