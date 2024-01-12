package com.foss.foss.data.mapper

import com.foss.foss.data.dto.MatchDto
import com.foss.foss.data.dto.MatchesDto
import com.foss.foss.model.Match
import com.foss.foss.model.MatchType
import com.foss.foss.model.Score
import com.foss.foss.model.WinDrawLose
import java.time.LocalDate

fun MatchesDto.toDomain(): List<Match> {
    return matchResponse.map { it.toDomain(memberInfo.nickname) }
}

fun MatchDto.toDomain(nickname: String): Match {
    return Match(
        date = LocalDate.parse(timestamp),
        manOfTheMatch = matchDetail.squads.maxBy { it.spRating }.pid,
        matchType = when (matchType) {
            40 -> MatchType.CLASSIC_ONE_TO_ONE
            50 -> MatchType.OFFICIAL
            else -> throw IllegalStateException()
        },
        opponentName = opponentNickname,
        winDrawLose = when (result) {
            "승" -> WinDrawLose.WIN
            "무" -> WinDrawLose.DRAW
            "패" -> WinDrawLose.LOSE
            else -> throw IllegalStateException()
        },
        score = Score(goals[nickname]!!, goals[opponentNickname]!!),
    )
}
