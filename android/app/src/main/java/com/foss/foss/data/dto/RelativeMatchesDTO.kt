package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelativeMatchesDTO(
    @SerialName("memberInfo")
    val memberInfo: MemberInfoDto,
    @SerialName("relativeMatchResponse")
    val relativeMatches: List<RelativeMatchDTO>
)
