package com.foss.foss.data.service

import com.foss.foss.data.dto.FcEventDTO
import retrofit2.Response
import retrofit2.http.GET

interface EventService {

    @GET("/api/events")
    suspend fun fetchFcEvents(): Response<List<FcEventDTO>>
}
