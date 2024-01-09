package com.foss.foss.model

import com.foss.foss.model.MatchMapper.toUiModel

object RelativeMatchMapper {

    fun RelativeMatch.toUiModel(): RelativeMatchUiModel = RelativeMatchUiModel(
        opponentName = opponentName,
        numberOfGames = winDrawLoses.numberOfGames,
        numberOfWins = winDrawLoses.numberOfWins,
        numberOfDraws = winDrawLoses.numberOfDraws,
        numberOfLoses = winDrawLoses.numberOfLoses,
        recentMatchDate = recentMatchDate,
        goal = totalScore.point,
        conceded = totalScore.otherPoint,
        matchDetails = matchDetails.map { it.toUiModel() }
    )
}
