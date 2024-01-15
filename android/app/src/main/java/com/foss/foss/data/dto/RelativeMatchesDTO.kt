package com.foss.foss.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelativeMatchesDTO(
    @SerialName("memberInfo")
    val memberInfo: MemberInfo,
    @SerialName("relativeMatchResponse")
    val relativeMatchResponse: List<RelativeMatchDTO>
)

@Serializable
data class MemberInfo(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("level")
    val level: Int,
    @SerialName("renewal")
    val renewal: String
)

@Serializable
data class RelativeMatchDTO(
    @SerialName("lastDate")
    val lastDate: String,
    @SerialName("opponentNickname")
    val opponentNickname: String,
    @SerialName("win")
    val win: Int,
    @SerialName("tie")
    val tie: Int,
    @SerialName("lose")
    val lose: Int,
    @SerialName("gain")
    val gain: Int,
    @SerialName("loss")
    val loss: Int,
    @SerialName("matchResponse")
    val matchResponse: List<MatchDTO>
)

@Serializable
data class MatchDTO(
    @SerialName("matchType")
    val matchType: Int,
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("result")
    val result: String,
    @SerialName("goals")
    val goals: Map<String, Int>,
    @SerialName("nickName")
    val nickName: String,
    @SerialName("opponentNickname")
    val opponentNickname: String,
    @SerialName("matchDetail")
    val matchDetail: MatchDetail
)

@Serializable
data class MatchDetail(
    @SerialName("possession")
    val possession: Int,
    @SerialName("shootCount")
    val shootCount: Int,
    @SerialName("effectiveShootCount")
    val effectiveShootCount: Int,
    @SerialName("passCount")
    val passCount: Int,
    @SerialName("passSuccessRate")
    val passSuccessRate: Int,
    @SerialName("squads")
    val squads: List<Squad>
)

@Serializable
data class Squad(
    @SerialName("pid")
    val pid: Int,
    @SerialName("position")
    val position: Int,
    @SerialName("grade")
    val grade: Int,
    @SerialName("spRating")
    val spRating: Double
)
