package com.foss.foss.data

import com.foss.foss.model.Nickname
import com.foss.foss.model.RelativeMatch
import com.foss.foss.model.Score
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.WinDrawLoses
import com.foss.foss.repository.RelativeMatchRepository
import java.time.LocalDate

class FakeRelativeMatchRepository : RelativeMatchRepository {

    override fun fetchRelativeMatches(nickname: Nickname): Result<List<RelativeMatch>> = runCatching {
        listOf(
            RelativeMatch(
                Nickname("ClintonHinton"),
                recentMatchDate = LocalDate.of(2023, 12, 2),
                winDrawLoses = WinDrawLoses(
                    listOf(
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.LOSE,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW
                    )
                ),
                totalScore = Score(point = 31, otherPoint = 23)
            ),
            RelativeMatch(
                Nickname("신공학관캣대디"),
                recentMatchDate = LocalDate.of(2023, 12, 2),
                winDrawLoses = WinDrawLoses(
                    listOf(
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.LOSE,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW
                    )
                ),
                totalScore = Score(point = 31, otherPoint = 23)
            ),
            RelativeMatch(
                Nickname("신공학관캣맘"),
                recentMatchDate = LocalDate.of(2023, 12, 2),
                winDrawLoses = WinDrawLoses(
                    listOf(
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.LOSE,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW,
                        WinDrawLose.WIN,
                        WinDrawLose.DRAW
                    )
                ),
                totalScore = Score(point = 31, otherPoint = 23)
            )
        )
    }
}
