package com.foss.foss.data.repository

import com.foss.foss.data.dto.NicknameDto
import com.foss.foss.data.mapper.MatchesDtoMapper.toDomainModel
import com.foss.foss.data.service.RecentMatchService
import com.foss.foss.model.Match
import com.foss.foss.model.MatchMapper.toIntType
import com.foss.foss.model.MatchType
import com.foss.foss.repository.MatchRepository
import java.io.IOException
import javax.inject.Inject

class DefaultMatchRepository @Inject constructor(
    private val recentMatchService: RecentMatchService
) : MatchRepository {

    override suspend fun requestRefresh(nickname: String) {
        return run {
            val response = recentMatchService.requestRefresh(NicknameDto(nickname))

            if (response.isSuccessful) {
                Unit
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }
    override suspend fun fetchMatches(nickname: String, matchType: MatchType): List<Match> {
        return run {
            val response = recentMatchService.fetchMatches(0, nickname, matchType.toIntType())

            if (response.isSuccessful) {
                val body = response.body()

                body?.toDomainModel() ?: throw Exception("Response body is null")
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }
}
