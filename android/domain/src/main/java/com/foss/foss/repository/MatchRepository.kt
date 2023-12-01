package com.foss.foss.repository

import com.foss.foss.model.Match

interface MatchRepository {

    fun fetchMatches(nickname: String): Result<List<Match>>
}
