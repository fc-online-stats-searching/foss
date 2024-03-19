package com.foss.foss.data.mapper

import com.foss.foss.data.dto.SquadDto
import com.foss.foss.model.MatchType
import com.foss.foss.model.WinDrawLose
import java.time.LocalDate
import java.time.LocalDateTime

class CommonDtoMapper {

    fun mapToManOfTheMatch(squads: List<SquadDto>): Int? {
        if (squads.isEmpty()) return null
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

    fun mapToDate(dateTime: String): LocalDate {
        return LocalDate.of(getYear(dateTime), getMonth(dateTime), getYear(dateTime))
    }

    fun mapToTime(dateTime: String): LocalDateTime {
        return LocalDateTime.of(
            getYear(dateTime),
            getMonth(dateTime),
            getDay(dateTime),
            getHour(dateTime),
            getMinute(dateTime)
        )
    }

    private fun getYear(dateTime: String): Int = dateTime.substring(0, 4).toInt()

    private fun getMonth(dateTime: String): Int = dateTime.substring(5, 7).toInt()

    private fun getDay(dateTime: String): Int = dateTime.substring(8, 10).toInt()

    private fun getHour(dateTime: String): Int = dateTime.substring(11, 13).toInt()

    private fun getMinute(dateTime: String): Int = dateTime.substring(14, 16).toInt()
}
