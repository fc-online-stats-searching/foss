package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchesDto(
    @SerialName("memberInfo")
    val memberInfo: MemberInfoDto,
    @SerialName("matchResponse")
    val matchResponse: List<MatchDto>
)
