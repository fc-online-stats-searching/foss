package com.foss.foss.data.datasource

import com.foss.foss.data.dto.NicknameDto
import com.foss.foss.data.dto.RelativeMatchesDTO
import com.foss.foss.data.service.RelativeMatchService
import java.io.IOException

class RelativeMatchDataSource(
    private val relativeMatchService: RelativeMatchService
) {

    suspend fun requestRefresh(nickname: String): Result<Unit> {
        return runCatching {
            val response = relativeMatchService.requestRefresh(NicknameDto(nickname))

            if (response.isSuccessful) {
                Unit
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }

    suspend fun fetchRelativeMatches(nickname: String): Result<RelativeMatchesDTO> {
        return runCatching {
            val response = relativeMatchService.fetchRelativeMatches(nickname)

            if (response.isSuccessful) {
                val body = response.body()
                body ?: throw Exception("Response body is null")
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }
}