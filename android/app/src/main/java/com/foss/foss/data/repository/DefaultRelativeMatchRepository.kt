package com.foss.foss.data.repository

import com.foss.foss.data.datasource.RelativeMatchDataSource
import com.foss.foss.data.mapper.RelativeMatchDtoMapper.toDomainModel
import com.foss.foss.model.RelativeMatch
import com.foss.foss.repository.RelativeMatchRepository

class DefaultRelativeMatchRepository(
    private val relativeMatchDataSource: RelativeMatchDataSource
) : RelativeMatchRepository {

    override suspend fun requestRefresh(nickname: String) {
        relativeMatchDataSource.requestRefresh(nickname).getOrThrow()
    }

    override suspend fun fetchRelativeMatches(nickname: String): Result<List<RelativeMatch>> {
        return runCatching {
            relativeMatchDataSource.fetchRelativeMatches(nickname)
                .getOrThrow()
                .relativeMatches
                .map { it.toDomainModel(nickname) }
        }
    }
}
