import com.foss.foss.feature.statsearching.recent.RecentMatchesViewModel
import com.foss.foss.model.Match
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchType
import com.foss.foss.model.WinDrawLose
import com.foss.foss.model.legacy.Score
import com.foss.foss.repository.MatchRepository
import com.foss.foss.util.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate


class RecentMatchesViewModelTest {

    private lateinit var matchRepository: MatchRepository
    private lateinit var recentMatchesViewModel: RecentMatchesViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        matchRepository = mockk()
        recentMatchesViewModel = RecentMatchesViewModel(matchRepository)
    }

    @Test
    fun `경기정보 가져오기 성공 시 UiState를 Success로 업데이트한다`() {
        // given
        val nickname = "사용자 이름"
        val matches = listOf(
            Match(
                date = LocalDate.of(2023, 12, 20),
                matchType = MatchType.OFFICIAL,
                manOfTheMatch = 1,
                otherSideNickname = "테스트",
                winDrawLose = WinDrawLose.WIN,
                score = Score(2, 1)
            )
        )

        coEvery {
            matchRepository.fetchMatches(nickname)
        } returns Result.success(matches)

        // when: 최근전적 기록을 요청하면
        recentMatchesViewModel.fetchMatches(nickname)

        // then
        val uiState = recentMatchesViewModel.uiState.value
        assertEquals(
            UiState.Success(matches.map {
                it.toUiModel()
            }),
            uiState
        )
    }

    @Test
    fun `매치정보 가져오기 실패 시 uiState를 Error로 업데이트한다`() {
        // given
        val nickname = "사용자 이름"
        val errorMessage = "Failed"

        coEvery {
            matchRepository.fetchMatches(nickname)
        } returns Result.failure(Throwable(errorMessage))

        // when: 최근전적 기록 요청을 하면
        recentMatchesViewModel.fetchMatches(nickname)

        // then
        val uiState = recentMatchesViewModel.uiState.value
        assertEquals(UiState.Error, uiState)
    }
}
