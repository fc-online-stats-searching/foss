package com.foss.foss.data.service

import com.foss.foss.data.dto.MatchesDto
import com.foss.foss.data.dto.RefreshDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RecentMatchService {
    @POST("/api/refresh")
    suspend fun requestRefresh(
        @Body nickname: String
    ): Response<RefreshDto>

    @GET("/api/matches")
    suspend fun fetchMatches(
        @Query("page") page: Int,
        @Query("nickname") nickname: String,
        @Query("matchType") matchType: Int
    ): Response<MatchesDto>
}
