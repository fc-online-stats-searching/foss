package com.searchingstats.repository

import com.searchingstats.model.MatchResult
import com.searchingstats.model.MatchType

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
