package com.foss.foss.data.repository

import com.foss.foss.data.service.RecentMatchService
import com.foss.foss.model.Match
import com.foss.foss.model.MatchType
import com.foss.foss.model.Nickname
import com.foss.foss.repository.MatchRepository

class DefaultMatchRepository(
    private val service: RecentMatchService,
) : MatchRepository {
    override fun fetchMatches(nickname: Nickname, matchType: MatchType): Result<List<Match>> {
        TODO("Not yet implemented")
    }

    override fun fetchMatchesBetweenUsers(
        nickname: Nickname,
        opponentNickname: Nickname,
    ): Result<List<Match>> {
        TODO("Not yet implemented")
    }
}
