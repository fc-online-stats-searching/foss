package com.foss.foss.data.dto

import kotlinx.serialization.*

@Serializable
data class RelativeMatchDTO(
    val memberInfo: MemberInfo,
    val relativeMatchResponse: List<RelativeMatchResponse>
)

@Serializable
data class MemberInfo(
    val nickname: String,
    val level: Int,
    val renewal: String
)

@Serializable
data class RelativeMatchResponse(
    val lastDate: String,
    val opponentNickname: String,
    val win: Int,
    val tie: Int,
    val lose: Int,
    val gain: Int,
    val loss: Int,
    val matchResponse: List<MatchResponse>
)

@Serializable
data class MatchResponse(
    val matchType: Int,
    val timestamp: String,
    val result: String,
    val goals: Map<String, Int>,
    val nickName: String,
    val opponentNickname: String,
    val matchDetail: MatchDetail
)

@Serializable
data class MatchDetail(
    val possession: Int,
    val shootCount: Int,
    val effectiveShootCount: Int,
    val passCount: Int,
    val passSuccessRate: Int,
    val squads: List<Squad>
)

@Serializable
data class Squad(
    val pid: Int,
    val position: Int,
    val grade: Int,
    val spRating: Double
)
