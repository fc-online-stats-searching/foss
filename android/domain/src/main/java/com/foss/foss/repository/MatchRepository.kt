package com.foss.foss.repository

import com.foss.foss.model.MatchResult
import com.foss.foss.model.MatchType

interface MatchRepository {

    suspend fun fetchMatchesId(
        userAccessId: String,
        matchType: MatchType
    ): Result<List<String>>

    suspend fun fetchMatchResult(
        userAccessId: String,
        matchId: String
    ): Result<MatchResult>
}
