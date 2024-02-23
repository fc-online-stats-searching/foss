package com.foss.foss.util

import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.WinDrawLoseUiModel
import java.time.LocalDate

object MockData {
    val recentMatch = listOf<MatchUiModel>(
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLoseUiModel.WIN,
            point = 1,
            otherPoint = 0,
        ),
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLoseUiModel.LOSE,
            point = 0,
            otherPoint = 1,
        ),
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLoseUiModel.WIN,
            point = 1,
            otherPoint = 0,
        ),
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLoseUiModel.LOSE,
            point = 0,
            otherPoint = 1,
        ),
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLoseUiModel.WIN,
            point = 1,
            otherPoint = 0,
        ),
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLoseUiModel.LOSE,
            point = 0,
            otherPoint = 1,
        ),
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLoseUiModel.WIN,
            point = 1,
            otherPoint = 0,
        ),
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLoseUiModel.LOSE,
            point = 0,
            otherPoint = 1,
        ),
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "신공학관캣맘",
            winDrawLose = WinDrawLoseUiModel.WIN,
            point = 1,
            otherPoint = 0,
        ),
        MatchUiModel(
            date = LocalDate.now(),
            matchType = MatchTypeUiModel.OFFICIAL,
            manOfTheMatch = null,
            opponentName = "똥찔긴형",
            winDrawLose = WinDrawLoseUiModel.LOSE,
            point = 0,
            otherPoint = 1,
        ),
    )
}
