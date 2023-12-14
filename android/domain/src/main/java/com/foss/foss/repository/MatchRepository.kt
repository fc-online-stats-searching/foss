package com.foss.foss.repository

import com.foss.foss.model.Match

/**
 * todo: StatRepository로 이름 변경하기
 */
interface MatchRepository {

    fun fetchMatches(nickname: String): Result<List<Match>>
}
