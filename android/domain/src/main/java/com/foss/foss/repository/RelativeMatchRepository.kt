package com.foss.foss.repository

import com.foss.foss.model.Nickname
import com.foss.foss.model.RelativeMatch

interface RelativeMatchRepository {

    fun fetchRelativeMatches(nickname: Nickname): Result<List<RelativeMatch>>
}
