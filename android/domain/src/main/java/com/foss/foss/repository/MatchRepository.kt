package com.foss.foss.repository

import com.foss.foss.model.Match
import com.foss.foss.model.Nickname

/**
 * todo: StatRepository로 이름 변경하기
 */
interface MatchRepository {

    fun fetchMatches(nickname: Nickname): Result<List<Match>>
    fun fetchMatchesBetweenUsers(nickname: Nickname, opponentNickname: Nickname): Result<List<Match>>
}
