package com.foss.foss.data.service

import com.foss.foss.data.entity.response.MaxRankResponse
import com.foss.foss.data.entity.response.UserResponse
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
