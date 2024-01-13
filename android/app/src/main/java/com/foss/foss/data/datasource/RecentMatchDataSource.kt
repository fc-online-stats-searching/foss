package com.foss.foss.data.datasource

import com.foss.foss.data.dto.MatchesDto
import com.foss.foss.data.service.RecentMatchService
import com.foss.foss.model.MatchType
import java.io.IOException

class RecentMatchDataSource(
    private val service: RecentMatchService,
) {
    suspend fun fetchMatches(nickname: String, matchType: MatchType): Result<MatchesDto> {
        return runCatching {
            val type = when (matchType) {
                MatchType.OFFICIAL -> 50
                MatchType.CLASSIC_ONE_TO_ONE -> 40
                MatchType.ALL -> 10
                else -> 10
            }

            val response = service.fetchMatches(1, nickname, type)

            if (response.isSuccessful) {
                val body = response.body()
                body ?: throw Exception("Response body is null")
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }
}
