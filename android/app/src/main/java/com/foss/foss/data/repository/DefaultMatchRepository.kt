package com.foss.foss.data.repository

import com.foss.foss.data.mapper.toDomain
import com.foss.foss.data.service.RecentMatchService
import com.foss.foss.model.Match
import com.foss.foss.model.MatchType
import com.foss.foss.repository.MatchRepository

class DefaultMatchRepository(
    private val service: RecentMatchService,
) : MatchRepository {
    override suspend fun fetchMatches(nickname: String, matchType: MatchType): Result<List<Match>> {
        return runCatching {
            service.fetchMatches(1, nickname, 10).getOrThrow().toDomain()
        }
    }

    override suspend fun fetchMatchesBetweenUsers(
        nickname: String,
        opponentNickname: String,
    ): Result<List<Match>> {
        TODO("Not yet implemented")
    }
}
