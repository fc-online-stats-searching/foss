package com.foss.foss.data.repository

import com.foss.foss.data.datasource.RecentMatchDataSource
import com.foss.foss.data.mapper.MatchesDtoMapper.toDomainModel
import com.foss.foss.model.Match
import com.foss.foss.model.MatchType
import com.foss.foss.repository.MatchRepository

class DefaultMatchRepository(
    private val recentMatchDataSource: RecentMatchDataSource
) : MatchRepository {
    override suspend fun fetchMatches(nickname: String, matchType: MatchType): Result<List<Match>> {
        return runCatching {
            recentMatchDataSource.fetchMatches(nickname, matchType)
                .getOrThrow()
                .toDomainModel()
        }
    }
}
