package com.foss.foss.util

import com.foss.foss.model.DivisionUiModel
import com.foss.foss.model.RelativeMatchUiModel
import java.time.LocalDate

object MockRelativeMatchData {
    val dummyMatches = listOf(
        RelativeMatchUiModel(
            opponentName = "똥질긴형",
            divisionUiModel = DivisionUiModel.SUPER_CHALLENGER,
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
            divisionUiModel = DivisionUiModel.SUPER_CHALLENGER,
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
            divisionUiModel = DivisionUiModel.SUPER_CHALLENGER,
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
            divisionUiModel = DivisionUiModel.SUPER_CHALLENGER,
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
            divisionUiModel = DivisionUiModel.SUPER_CHALLENGER,
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
            divisionUiModel = DivisionUiModel.SUPER_CHALLENGER,
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
            divisionUiModel = DivisionUiModel.SUPER_CHALLENGER,
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
