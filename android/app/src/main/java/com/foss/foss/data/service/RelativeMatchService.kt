package com.foss.foss.data.service

import com.foss.foss.data.dto.RelativeMatchesDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RelativeMatchService {
    @GET("api/matches/relative")
    suspend fun fetchRelativeMatches(
        @Query("nickname") nickname: String
    ): Response<RelativeMatchesDTO>
}
