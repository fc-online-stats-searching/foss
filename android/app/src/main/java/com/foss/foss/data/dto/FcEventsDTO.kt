package com.foss.foss.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class FcEventsDTO(
    val value: List<FcEventDTO>
)
