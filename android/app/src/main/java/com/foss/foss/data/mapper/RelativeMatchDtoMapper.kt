package com.foss.foss.data.mapper

import com.foss.foss.data.dto.RelativeMatchDTO
import com.foss.foss.data.mapper.MatchesDtoMapper.toDomainModel
import com.foss.foss.model.RelativeMatch
import com.foss.foss.model.Score
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.WinDrawLoses

object RelativeMatchDtoMapper {

    private val commonDtoMapper = CommonDtoMapper()

    fun RelativeMatchDTO.toDomainModel(nickname: String): RelativeMatch = RelativeMatch(
        opponentName = opponentNickname,
        recentMatchDate = commonDtoMapper.mapToTime(lastDate),
        winDrawLoses = WinDrawLoses(mapToWinDrawLoses(win, tie, lose)),
        totalScore = Score(gain, loss),
        matchDetails = matchResponse.map { it.toDomainModel(nickname) }
    )

    private fun mapToWinDrawLoses(win: Int, tie: Int, lose: Int): List<WinDrawLose> {
        return List(win) { WinDrawLose.WIN } + List(tie) { WinDrawLose.DRAW } + List(lose) { WinDrawLose.LOSE }
    }
}
