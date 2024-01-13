// package com.foss.foss.data
//
// import com.foss.foss.model.Match
// import com.foss.foss.model.MatchType
// import com.foss.foss.model.RelativeMatch
// import com.foss.foss.model.Score
// import com.foss.foss.model.WinDrawLose
// import com.foss.foss.model.WinDrawLoses
// import com.foss.foss.repository.RelativeMatchRepository
// import java.time.LocalDate
//
// class FakeRelativeMatchRepository : RelativeMatchRepository {
//
//    override fun fetchRelativeMatches(nickname: String): Result<List<RelativeMatch>> = runCatching {
//        listOf(
//            RelativeMatch(
//                "ClintonHinton",
//                recentMatchDate = LocalDate.of(2023, 12, 2),
//                winDrawLoses = WinDrawLoses(
//                    listOf(
//                        WinDrawLose.WIN,
//                        WinDrawLose.DRAW,
//                        WinDrawLose.LOSE,
//                        WinDrawLose.WIN,
//                        WinDrawLose.DRAW,
//                        WinDrawLose.WIN,
//                        WinDrawLose.DRAW
//                    )
//                ),
//                totalScore = Score(point = 31, otherPoint = 23),
//                matchDetails = listOf(
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.WIN,
//                        score = Score(point = 2, otherPoint = 1)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣대디",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 1, otherPoint = 2)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.DRAW,
//                        score = Score(point = 1, otherPoint = 1)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣대디",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 1, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.WIN,
//                        score = Score(point = 5, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 1, otherPoint = 6)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣맘",
//                        winDrawLose = WinDrawLose.DRAW,
//                        score = Score(point = 3, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 2, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣대디",
//                        winDrawLose = WinDrawLose.WIN,
//                        score = Score(point = 1, otherPoint = 0)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 2, otherPoint = 5)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.DRAW,
//                        score = Score(point = 3, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣맘",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 0, otherPoint = 1)
//                    )
//                )
//            ),
//            RelativeMatch(
//                "신공학관캣대디",
//                recentMatchDate = LocalDate.of(2023, 12, 2),
//                winDrawLoses = WinDrawLoses(
//                    listOf(
//                        WinDrawLose.WIN,
//                        WinDrawLose.DRAW,
//                        WinDrawLose.LOSE,
//                        WinDrawLose.WIN,
//                        WinDrawLose.DRAW,
//                        WinDrawLose.WIN,
//                        WinDrawLose.DRAW
//                    )
//                ),
//                totalScore = Score(point = 31, otherPoint = 23),
//                matchDetails = listOf(
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.WIN,
//                        score = Score(point = 2, otherPoint = 1)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣대디",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 1, otherPoint = 2)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.DRAW,
//                        score = Score(point = 1, otherPoint = 1)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣대디",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 1, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.WIN,
//                        score = Score(point = 5, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 1, otherPoint = 6)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣맘",
//                        winDrawLose = WinDrawLose.DRAW,
//                        score = Score(point = 3, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 2, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣대디",
//                        winDrawLose = WinDrawLose.WIN,
//                        score = Score(point = 1, otherPoint = 0)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 2, otherPoint = 5)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.DRAW,
//                        score = Score(point = 3, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣맘",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 0, otherPoint = 1)
//                    )
//                )
//
//            ),
//            RelativeMatch(
//                "신공학관캣맘",
//                recentMatchDate = LocalDate.of(2023, 12, 2),
//                winDrawLoses = WinDrawLoses(
//                    listOf(
//                        WinDrawLose.WIN,
//                        WinDrawLose.DRAW,
//                        WinDrawLose.LOSE,
//                        WinDrawLose.WIN,
//                        WinDrawLose.DRAW,
//                        WinDrawLose.WIN,
//                        WinDrawLose.DRAW
//                    )
//                ),
//                totalScore = Score(point = 31, otherPoint = 23),
//                matchDetails = listOf(
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.WIN,
//                        score = Score(point = 2, otherPoint = 1)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣대디",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 1, otherPoint = 2)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.DRAW,
//                        score = Score(point = 1, otherPoint = 1)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣대디",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 1, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.WIN,
//                        score = Score(point = 5, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 1, otherPoint = 6)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣맘",
//                        winDrawLose = WinDrawLose.DRAW,
//                        score = Score(point = 3, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 2, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣대디",
//                        winDrawLose = WinDrawLose.WIN,
//                        score = Score(point = 1, otherPoint = 0)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 2, otherPoint = 5)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "ClintonHinton",
//                        winDrawLose = WinDrawLose.DRAW,
//                        score = Score(point = 3, otherPoint = 3)
//                    ),
//                    Match(
//                        date = LocalDate.of(2023, 12, 2),
//                        matchType = MatchType.OFFICIAL,
//                        manOfTheMatch = 1,
//                        opponentName = "신공학관캣맘",
//                        winDrawLose = WinDrawLose.LOSE,
//                        score = Score(point = 0, otherPoint = 1)
//                    )
//                )
//            )
//        )
//    }
// }
