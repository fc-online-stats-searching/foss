import com.foss.foss.model.Match
import com.foss.foss.model.MatchType
import com.foss.foss.model.Score
import com.foss.foss.model.WinDrawLose
import java.time.LocalDateTime

object MatchFixture {

    fun create() = List(3) {
        Match(
            date = LocalDateTime.now(),
            matchType = MatchType.OFFICIAL,
            opponentName = "신공학관캣대디",
            manOfTheMatch = 1,
            winDrawLose = WinDrawLose.WIN,
            score = Score(point = 31, otherPoint = 23),
        )
    }
}
