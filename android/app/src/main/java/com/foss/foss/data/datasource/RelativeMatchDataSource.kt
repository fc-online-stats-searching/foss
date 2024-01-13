package com.foss.foss.data.datasource

import com.foss.foss.data.dto.RelativeMatchDTO
import com.foss.foss.data.service.RelativeMatchService
import java.io.IOException

class RelativeMatchDataSource(
    private val relativeMatchService: RelativeMatchService
) {

    suspend fun fetchRelativeMatches(nickname: String): Result<RelativeMatchDTO> {
        try {
            val response = relativeMatchService.fetchRelativeMatches(nickname)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Result.success(body)
                } else {
                    return Result.failure(Exception("Response body is null"))
                }
            } else {
                return Result.failure(IOException("Request failed with code: ${response.code()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
