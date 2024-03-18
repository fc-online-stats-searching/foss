package com.foss.foss.util

import com.foss.foss.model.RelativeMatchUiModel
import java.time.LocalDate

object MockRelativeMatchData {
    val dummyMatches = listOf(
        RelativeMatchUiModel(
            opponentName = "똥질긴형",
            numberOfGames = 10,
            numberOfWins = 6,
            numberOfDraws = 2,
            numberOfLoses = 2,
            recentMatchDate = LocalDate.now(),
            goal = 15,
            conceded = 10,
            matchDetails = emptyList()
        ),
        RelativeMatchUiModel(
            opponentName = "신공학관캣대디",
            numberOfGames = 2,
            numberOfWins = 4,
            numberOfDraws = 2,
            numberOfLoses = 2,
            recentMatchDate = LocalDate.now().minusDays(1),
            goal = 12,
            conceded = 8,
            matchDetails = emptyList()
        ),
        RelativeMatchUiModel(
            opponentName = "긴똥질형",
            numberOfGames = 7,
            numberOfWins = 2,
            numberOfDraws = 1,
            numberOfLoses = 4,
            recentMatchDate = LocalDate.now(),
            goal = 15,
            conceded = 10,
            matchDetails = emptyList()
        ),
        RelativeMatchUiModel(
            opponentName = "신공학관캣맘",
            numberOfGames = 5,
            numberOfWins = 3,
            numberOfDraws = 1,
            numberOfLoses = 1,
            recentMatchDate = LocalDate.now().minusDays(1),
            goal = 12,
            conceded = 8,
            matchDetails = emptyList()
        ),
        RelativeMatchUiModel(
            opponentName = "가느다란물방울",
            numberOfGames = 7,
            numberOfWins = 2,
            numberOfDraws = 1,
            numberOfLoses = 4,
            recentMatchDate = LocalDate.now(),
            goal = 15,
            conceded = 10,
            matchDetails = emptyList()
        ),
        RelativeMatchUiModel(
            opponentName = "티없이맑은하늘",
            numberOfGames = 5,
            numberOfWins = 1,
            numberOfDraws = 2,
            numberOfLoses = 2,
            recentMatchDate = LocalDate.now().minusDays(1),
            goal = 12,
            conceded = 8,
            matchDetails = emptyList()
        ),
        RelativeMatchUiModel(
            opponentName = "만찐두빵",
            numberOfGames = 5,
            numberOfWins = 1,
            numberOfDraws = 2,
            numberOfLoses = 2,
            recentMatchDate = LocalDate.now().minusDays(1),
            goal = 12,
            conceded = 8,
            matchDetails = emptyList()
        )

    )
}