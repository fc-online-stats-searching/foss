import com.foss.foss.model.Match
import com.foss.foss.model.MatchType
import com.foss.foss.model.Nickname
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.legacy.Score
import java.time.LocalDate

object MatchFixture {

    fun create() = List(3) {
        Match(
            date = LocalDate.of(2023, 12, 18),
            matchType = MatchType.DIRECTOR,
            otherSideNickname = Nickname("신공학관캣대디"),
            manOfTheMatch = 1,
            winDrawLose = WinDrawLose.WIN,
            score = Score(point = 31, otherPoint = 23)
        )
    }
}
