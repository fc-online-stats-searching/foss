import com.foss.foss.model.MatchResult
import com.foss.foss.model.MatchType
import com.foss.foss.model.Score
import com.foss.foss.model.WinDrawLose

object MatchFixture {

    fun createMatchId() = listOf("abcdef1")

    fun createMatchResult() = MatchResult(
        matchType = MatchType.OFFICIAL,
        otherSideNickname = "Lillie Cherry",
        winDrawLose = WinDrawLose.WIN,
        score = Score(
            point = 2,
            otherPoint = 0
        )
    )
}
