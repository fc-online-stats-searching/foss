package com.foss.foss.repository

import com.foss.foss.model.FcEvent

interface EventRepository {

    suspend fun fetchEvents(): List<FcEvent>
}
