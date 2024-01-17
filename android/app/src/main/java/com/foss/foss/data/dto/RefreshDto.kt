package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshDto(
    @SerialName("success")
    val success: Boolean,
    @SerialName("code")
    val code: Int,
    @SerialName("msg")
    val msg: String,
    @SerialName("data")
    val memberInfo: MemberInfoDto
)
