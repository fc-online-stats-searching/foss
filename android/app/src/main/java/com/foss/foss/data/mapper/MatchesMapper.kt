package com.foss.foss.data.mapper

import com.foss.foss.data.dto.MatchDto
import com.foss.foss.data.dto.MatchesDto
import com.foss.foss.model.Match
import com.foss.foss.model.MatchType
import com.foss.foss.model.Score
import com.foss.foss.model.WinDrawLose
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun MatchesDto.toDomain(): List<Match> {
    return matchResponse.map { it.toDomain(memberInfo.nickname) }
}

fun MatchDto.toDomain(nickname: String): Match {
    return Match(
        date = timestamp.toLocalDate(),
        manOfTheMatch = matchDetail.squads.maxBy { it.spRating }.pid,
        matchType = matchType.divide(),
        opponentName = opponentNickname,
        winDrawLose = result.divide(),
        score = Score(goals[nickname]!!, goals[opponentNickname]!!),
    )
}

private fun Int.divide(): MatchType {
    return when (this) {
        40 -> MatchType.CLASSIC_ONE_TO_ONE
        50 -> MatchType.OFFICIAL
        else -> throw IllegalStateException()
    }
}

private fun String.divide(): WinDrawLose {
    return when (this) {
        "승" -> WinDrawLose.WIN
        "무" -> WinDrawLose.DRAW
        "패" -> WinDrawLose.LOSE
        else -> throw IllegalStateException()
    }
}

private fun String.toLocalDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return LocalDateTime.parse(this, formatter).toLocalDate()
}
