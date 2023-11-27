package com.foss.foss.repository.legacy

import com.foss.foss.model.legacy.MatchResult
import com.foss.foss.model.legacy.MatchType

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
