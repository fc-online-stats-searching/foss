package com.foss.foss.data.datasource

import com.foss.foss.data.dto.MatchesDto
import com.foss.foss.data.service.RecentMatchService
import com.foss.foss.model.MatchMapper.toIntType
import com.foss.foss.model.MatchType
import java.io.IOException

class RecentMatchDataSource(
    private val recentMatchService: RecentMatchService
) {

    suspend fun requestRefresh(nickname: String): Result<Unit> {
        return runCatching {
            val response = recentMatchService.requestRefresh(nickname)

            if (response.isSuccessful) {
                Unit
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }

    suspend fun fetchMatches(nickname: String, matchType: MatchType): Result<MatchesDto> {
        return runCatching {
            val response = recentMatchService.fetchMatches(0, nickname, matchType.toIntType())

            if (response.isSuccessful) {
                val body = response.body()
                body ?: throw Exception("Response body is null")
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }
}
