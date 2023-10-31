import com.searchingstats.model.MatchResult
import com.searchingstats.model.MatchType
import com.searchingstats.model.WinDrawLose

object MatchFixture {

    fun createMatchId() = listOf("abcdef1")

    fun createMatchResult() = MatchResult(
        matchType = MatchType.OFFICIAL,
        otherSideNickname = "Lillie Cherry",
        winDrawLose = WinDrawLose.WIN
    )
}
