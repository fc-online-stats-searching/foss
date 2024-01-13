package com.foss.foss.data.datasource

import com.foss.foss.data.dto.MatchesDto
import com.foss.foss.data.service.RecentMatchService
import com.foss.foss.model.MatchType
import java.io.IOException

class RecentMatchDataSource(
    private val service: RecentMatchService,
) {
    suspend fun fetchMatches(nickname: String, matchType: MatchType): Result<MatchesDto> {
        val type = when (matchType) {
            MatchType.OFFICIAL -> 50
            MatchType.CLASSIC_ONE_TO_ONE -> 40
            else -> 10
        }
        try {
            val response = service.fetchMatches(1, nickname, 10)

            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(IOException("Request failed with code: ${response.code()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
