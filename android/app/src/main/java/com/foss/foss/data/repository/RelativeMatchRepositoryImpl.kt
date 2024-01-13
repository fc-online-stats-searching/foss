package com.foss.foss.data.repository

import com.foss.foss.data.datasource.RelativeMatchDataSource
import com.foss.foss.data.mapper.RelativeMatchDtoMapper.toDomainModel
import com.foss.foss.model.RelativeMatch
import com.foss.foss.repository.RelativeMatchRepository

class RelativeMatchRepositoryImpl(
    private val relativeMatchDataSource: RelativeMatchDataSource
) : RelativeMatchRepository {

    override suspend fun fetchRelativeMatches(nickname: String): Result<List<RelativeMatch>> {
        try {
            val relativeMatchDTOResult = relativeMatchDataSource.fetchRelativeMatches(nickname)

            return relativeMatchDTOResult.fold(
                onSuccess = { relativeMatchDTO ->
                    val relativeMatchDtoToDomainModel =
                        relativeMatchDTO.relativeMatchResponse.map { it.toDomainModel() }
                    Result.success(relativeMatchDtoToDomainModel)
                },
                onFailure = { e ->
                    Result.failure(e)
                }
            )
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
