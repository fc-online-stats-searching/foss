package com.foss.foss.data.datasource

import com.foss.foss.data.dto.RelativeMatchDTO
import com.foss.foss.data.service.RelativeMatchService

class RelativeMatchDataSource(
    private val relativeMatchService: RelativeMatchService
) {
    suspend fun fetchRelativeMatches(nickname: String): Result<RelativeMatchDTO> {
        try {
            val relativeMatchDTO = relativeMatchService.fetchRelativeMatches(nickname)
            return relativeMatchDTO
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
