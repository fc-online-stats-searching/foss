import com.foss.foss.model.legacy.MatchResult
import com.foss.foss.model.legacy.MatchType
import com.foss.foss.model.legacy.Score
import com.foss.foss.model.legacy.WinDrawLose

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
