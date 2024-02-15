package com.foss.foss.repository

import com.foss.foss.model.RelativeMatch

interface RelativeMatchRepository {

    suspend fun requestRefresh(nickname: String)

    suspend fun fetchRelativeMatches(nickname: String): Result<List<RelativeMatch>>
}
