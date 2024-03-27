package com.foss.foss.data.mapper

import com.foss.foss.data.dto.RelativeMatchDTO
import com.foss.foss.data.mapper.MatchesDtoMapper.toDomainModel
import com.foss.foss.model.Division
import com.foss.foss.model.RelativeMatch
import com.foss.foss.model.Score
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.WinDrawLoses
import java.time.DateTimeException

object RelativeMatchDtoMapper {

    private val commonDtoMapper = CommonDtoMapper()

    fun RelativeMatchDTO.toDomainModel(nickname: String): RelativeMatch {
        val opponentName = opponentNickname
        val recentMatchDate = try {
            commonDtoMapper.mapToDate(lastDate)
        } catch (e: DateTimeException) {
            null
        }
        val divisionValue = matchResponse.firstOrNull()
            ?.opponentDivision
            ?.division
            ?: 0
        val winDrawLoses = WinDrawLoses(mapToWinDrawLoses(win, tie, lose))
        val totalScore = Score(gain, loss)
        val matchDetails = matchResponse.map { it.toDomainModel(nickname) }

        return RelativeMatch(
            opponentName = opponentName,
            opponentDivision = Division.instanceOf(divisionValue),
            recentMatchDate = recentMatchDate,
            winDrawLoses = winDrawLoses,
            totalScore = totalScore,
            matchDetails = matchDetails
        )
    }

    private fun mapToWinDrawLoses(win: Int, tie: Int, lose: Int): List<WinDrawLose> {
        return List(win) { WinDrawLose.WIN } + List(tie) { WinDrawLose.DRAW } + List(lose) { WinDrawLose.LOSE }
    }
}
