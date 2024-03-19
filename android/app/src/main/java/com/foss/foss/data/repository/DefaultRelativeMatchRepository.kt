package com.foss.foss.data.repository

import com.foss.foss.data.dto.NicknameDto
import com.foss.foss.data.mapper.RelativeMatchDtoMapper.toDomainModel
import com.foss.foss.data.service.RelativeMatchService
import com.foss.foss.model.RelativeMatch
import com.foss.foss.repository.RelativeMatchRepository
import java.io.IOException
import javax.inject.Inject

class DefaultRelativeMatchRepository @Inject constructor(
    private val relativeMatchService: RelativeMatchService
) : RelativeMatchRepository {

    override suspend fun requestRefresh(nickname: String) {
        runCatching {
            val response = relativeMatchService.requestRefresh(NicknameDto(nickname))

            if (response.isSuccessful) {
                Unit
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }

    override suspend fun fetchRelativeMatches(nickname: String): List<RelativeMatch> {
        return run {
            val response = relativeMatchService.fetchRelativeMatches(nickname)

            if (response.isSuccessful) {
                val body = response.body()?.relativeMatches

                body?.map {
                    it.toDomainModel(nickname)
                } ?: throw Exception("Response body is null")
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }
}
