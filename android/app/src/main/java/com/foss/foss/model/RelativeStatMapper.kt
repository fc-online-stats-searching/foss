package com.foss.foss.model

object RelativeStatMapper {

    fun RelativeMatch.toUiModel(): RelativeMatchUiModel = RelativeMatchUiModel(
        opponentName = opponentName.name,
        numberOfGames = winDrawLoses.numberOfGames,
        numberOfWins = winDrawLoses.numberOfWins,
        numberOfDraws = winDrawLoses.numberOfDraws,
        numberOfLoses = winDrawLoses.numberOfLoses,
        recentMatchDate = recentMatchDate,
        goal = totalScore.point,
        conceded = totalScore.otherPoint
    )
}
