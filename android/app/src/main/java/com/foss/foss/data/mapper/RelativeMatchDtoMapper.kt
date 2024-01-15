package com.foss.foss.data.mapper

import com.foss.foss.data.dto.MatchDTO
import com.foss.foss.data.dto.RelativeMatchDTO
import com.foss.foss.model.Match
import com.foss.foss.model.MatchType
import com.foss.foss.model.RelativeMatch
import com.foss.foss.model.Score
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.WinDrawLoses
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object RelativeMatchDtoMapper {

    fun MatchDTO.toDomainModel(): Match = Match(
        date = stringToLocalDate(timestamp),
        matchType = determineMatchType(matchType),
        manOfTheMatch = 1,
        opponentName = opponentNickname,
        winDrawLose = determineWinDrawLose(goals, nickName, opponentNickname),
        score = makeScores(goals, nickName, opponentNickname)
    )

    fun RelativeMatchDTO.toDomainModel(): RelativeMatch = RelativeMatch(
        opponentName = opponentNickname,
        recentMatchDate = stringToLocalDate(lastDate),
        winDrawLoses = WinDrawLoses(makeWinDrawLose(win, tie, lose)),
        totalScore = Score(gain, lose),
        matchDetails = matchResponse.map { it.toDomainModel() }
    )

    private fun stringToLocalDate(dateTimeString: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val localDateTime = LocalDateTime.parse(dateTimeString, formatter)
        return localDateTime.toLocalDate()
    }

    private fun makeWinDrawLose(win: Int, tie: Int, lose: Int): List<WinDrawLose> {
        return List(win) { WinDrawLose.WIN } + List(tie) { WinDrawLose.DRAW } + List(lose) { WinDrawLose.LOSE }
    }

    private fun determineWinDrawLose(
        goals: Map<String, Int>,
        myName: String,
        opponentName: String
    ): WinDrawLose {
        return when {
            goals[myName]!! > goals[opponentName]!! -> WinDrawLose.WIN
            goals[myName]!! < goals[opponentName]!! -> WinDrawLose.LOSE
            else -> WinDrawLose.DRAW
        }
    }

    private fun determineMatchType(matchType: Int): MatchType {
        return when (matchType) {
            40 -> MatchType.CLASSIC_ONE_TO_ONE
            else -> MatchType.OFFICIAL
        }
    }

    private fun makeScores(
        goals: Map<String, Int>,
        myName: String,
        opponentName: String
    ): Score = Score(
        goals[myName]!!,
        goals[opponentName]!!
    )
}
