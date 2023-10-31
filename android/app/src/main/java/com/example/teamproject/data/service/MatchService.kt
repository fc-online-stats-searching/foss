package com.example.teamproject.data.service

import com.example.teamproject.data.entity.response.MatchResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MatchService {

    @GET("users/{user_accessid}/matches")
    suspend fun fetchMatchesId(
        @Path("user_accessid")
        userAccessId: String,
        @Query("matchtype")
        matchType: Int,
        @Query("offset")
        offset: Int,
        @Query("limit")
        limit: Int
        /**
         * todo: we have to find that how to wrap this class
         */
    ): Response<List<String>>

    @GET("matches/{matchid}")
    suspend fun fetchMatchResult(
        @Path("matchid")
        matchId: String
    ): Response<MatchResultResponse>
}
