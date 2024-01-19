package com.foss.foss.repository

import com.foss.foss.model.Match
import com.foss.foss.model.MatchType

interface MatchRepository {

    suspend fun requestRefresh(nickname: String): Result<Unit>

    suspend fun fetchMatches(nickname: String, matchType: MatchType): Result<List<Match>>
}
