package com.foss.foss.data.legacy.datasource

import com.foss.foss.data.legacy.HttpError
import com.foss.foss.data.legacy.entity.request.MatchRequest
import com.foss.foss.data.legacy.entity.request.MatchesIdRequest
import com.foss.foss.data.legacy.entity.response.MatchResultResponse
import com.foss.foss.data.legacy.entity.response.MatchesIdResponse
import com.foss.foss.data.legacy.service.MatchService

class MatchRemoteDataSource(
    private val matchService: MatchService
) {

    suspend fun fetchMatchesId(matchesIdRequest: MatchesIdRequest): MatchesIdResponse {
        val response = matchService.fetchMatchesId(
            userAccessId = matchesIdRequest.userAccessId,
            matchType = matchesIdRequest.matchType,
            offset = matchesIdRequest.offset,
            limit = matchesIdRequest.limit
        )

        if (response.code() == 200) {
            response.body()?.let {
                return MatchesIdResponse(it)
            } ?: throw HttpError.EmptyBody(response)
        }
        throw HttpError.BadRequest(response)
    }

    suspend fun fetchMatchResult(matchRequest: MatchRequest): MatchResultResponse {
        val response = matchService.fetchMatchResult(matchRequest.matchId)

        if (response.code() == 200) {
            return response.body() ?: throw HttpError.EmptyBody(response)
        }

        throw HttpError.BadRequest(response)
    }
}
