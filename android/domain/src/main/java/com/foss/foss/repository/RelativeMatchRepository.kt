package com.foss.foss.repository

import com.foss.foss.model.RelativeMatch

interface RelativeMatchRepository {

    fun fetchRelativeMatches(nickname: String): Result<List<RelativeMatch>>
}
