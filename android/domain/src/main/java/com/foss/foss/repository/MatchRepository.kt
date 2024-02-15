package com.foss.foss.repository

import com.foss.foss.model.Match
import com.foss.foss.model.MatchType

interface MatchRepository {

    suspend fun requestRefresh(nickname: String)

    suspend fun fetchMatches(nickname: String, matchType: MatchType): List<Match>
}
