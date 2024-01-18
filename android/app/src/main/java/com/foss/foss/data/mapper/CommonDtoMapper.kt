package com.foss.foss.data.mapper

import com.foss.foss.data.dto.SquadDto
import com.foss.foss.model.MatchType
import com.foss.foss.model.WinDrawLose
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CommonDtoMapper {

    fun mapToManOfTheMatch(squads: List<SquadDto>): Int {
        return squads.maxBy { it.spRating }.pid % 1_000_000
    }

    fun mapToMatchType(matchTypes: Int): MatchType {
        return when (matchTypes) {
            40 -> MatchType.CLASSIC_ONE_TO_ONE
            50 -> MatchType.OFFICIAL
            else -> throw IllegalStateException()
        }
    }

    fun mapToWinDrawLose(winDrawLose: String): WinDrawLose {
        return when (winDrawLose) {
            "승" -> WinDrawLose.WIN
            "무" -> WinDrawLose.DRAW
            "패" -> WinDrawLose.LOSE
            else -> throw IllegalStateException()
        }
    }

    fun mapToTime(dateTime: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return LocalDateTime.parse(dateTime, formatter).toLocalDate()
    }
}
