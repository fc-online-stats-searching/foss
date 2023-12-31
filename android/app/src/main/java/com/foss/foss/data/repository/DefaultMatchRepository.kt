package com.foss.foss.data.repository

import com.foss.foss.data.datasource.MatchRemoteDataSource
import com.foss.foss.data.entity.request.MatchRequest
import com.foss.foss.data.entity.request.MatchesIdRequest
import com.foss.foss.data.entity.response.MatchInfoResponse
import com.foss.foss.data.entity.response.MatchResultResponse
import com.foss.foss.data.mapper.MatchMapper
import com.foss.foss.model.MatchResult
import com.foss.foss.model.MatchType
import com.foss.foss.model.Score
import com.foss.foss.repository.MatchRepository

class DefaultMatchRepository(
    private val matchRemoteDataSource: MatchRemoteDataSource
) : MatchRepository {

    // todo: need dependency injection
    private val matchMapper = MatchMapper()

    override suspend fun fetchMatchesId(
        userAccessId: String,
        matchType: MatchType
    ): Result<List<String>> = runCatching {
        matchRemoteDataSource
            .fetchMatchesId(
                matchesIdRequest = MatchesIdRequest(
                    userAccessId = userAccessId,
                    matchType = matchMapper.mapToEntity(matchType)
                )
            ).matchesId
    }

    override suspend fun fetchMatchResult(
        userAccessId: String,
        matchId: String
    ): Result<MatchResult> = runCatching {
        val match = matchRemoteDataSource.fetchMatchResult(MatchRequest(matchId))
        val matchInfo: MatchInfoResponse = match.getMatchInfoOf { it == userAccessId }
        val otherMatchInfo: MatchInfoResponse = match.getMatchInfoOf { it != userAccessId }
        val winDrawLose: String = matchInfo
            .matchDetailResponse
            .matchResult
        val point: Int = matchInfo
            .shootResponse
            .ownGoal
        val otherPoint: Int = otherMatchInfo
            .shootResponse
            .ownGoal

        MatchResult(
            matchType = matchMapper.mapToDomain(match.matchType),
            otherSideNickname = otherMatchInfo.nickname,
            winDrawLose = matchMapper.mapToDomain(winDrawLose),
            score = Score(
                point = point,
                otherPoint = otherPoint
            )
        )
    }

    private fun MatchResultResponse.getMatchInfoOf(condition: (accessId: String) -> Boolean) =
        matchesInfoResponse.find { condition(it.accessId) }
            ?: throw NoSuchElementException(ERROR_NO_SUCH_USER)

    companion object {
        private const val ERROR_NO_SUCH_USER = "해당 유저의 경기 결과가 존재하지 않습니다."
    }
}
