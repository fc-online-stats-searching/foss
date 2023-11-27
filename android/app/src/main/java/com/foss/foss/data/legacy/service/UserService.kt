package com.foss.foss.data.legacy.service

import com.foss.foss.data.legacy.entity.response.MaxRankResponse
import com.foss.foss.data.legacy.entity.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("users")
    suspend fun fetchUser(
        @Query("nickname")
        nickname: String
    ): Response<UserResponse>

    @GET("/fifaonline4/v1.0/users/{accessid}/maxdivision")
    suspend fun fetchMaxRank(
        @Path("accessid")
        accessId: String
    ): Response<MaxRankResponse>
}
