package com.foss.foss.data.repository

import com.foss.foss.data.dto.FcEventDTO
import com.foss.foss.data.mapper.EventMapper.toDomainModel
import com.foss.foss.data.service.EventService
import com.foss.foss.model.FcEvent
import com.foss.foss.repository.EventRepository
import java.io.IOException
import javax.inject.Inject

class DefaultEventRepository @Inject constructor(
    private val eventService: EventService
) : EventRepository {

    override suspend fun fetchEvents(): List<FcEvent> {
        return run {
            val response = eventService.fetchFcEvents()

            if (response.isSuccessful) {
                val body: List<FcEventDTO>? = response.body()

                body?.map { it.toDomainModel() } ?: throw Exception("Response body is null")
            } else {
                throw IOException("Request failed with code: ${response.code()}")
            }
        }
    }
}
