package com.foss.foss.repository

import com.foss.foss.model.Match
import com.foss.foss.model.MatchType
import com.foss.foss.model.Nickname

interface MatchRepository {

    fun fetchMatches(nickname: String, matchType: MatchType = MatchType.OFFICIAL): Result<List<Match>>

    fun fetchMatchesBetweenUsers(nickname: String, opponentNickname: String): Result<List<Match>>
}
