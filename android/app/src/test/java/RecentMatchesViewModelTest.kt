import app.cash.turbine.test
import com.foss.foss.feature.statsearching.recent.RecentMatchesEvent
import com.foss.foss.feature.statsearching.recent.RecentMatchesUiState
import com.foss.foss.feature.statsearching.recent.RecentMatchesViewModel
import com.foss.foss.model.Match
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.Nickname
import com.foss.foss.repository.MatchRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

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

    fun `최근전적 기록을 요청하면`() {
        recentMatchesViewModel.fetchMatches("이름")
    }

    fun `최근전적 기록 요청에 대한 결과가 다음과 같을 때`(result: Result<List<Match>>) {
        coEvery {
            matchRepository.fetchMatches(Nickname("이름"))
        } returns result
    }

    @Test
    fun `경기정보 가져오기 성공 시 UiState를 Success로 업데이트한다`() {
        // given
        val matches = MatchFixture.create()

        `최근전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.success(matches))

        // when
        `최근전적 기록을 요청하면`()

        val actual = recentMatchesViewModel.uiState.value

        // then
        val expected = RecentMatchesUiState.Success(matches.map { it.toUiModel() })

        assertEquals(expected, actual)
    }

    @Test
    fun `매치정보 가져오기 실패 시 Failed 이벤트가 발생한다`() = runTest {
        // given
        `최근전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.failure(Throwable()))

        recentMatchesViewModel.event.test {
            // when
            `최근전적 기록을 요청하면`()
            val actual = awaitItem()

            // then
            val expected = RecentMatchesEvent.Failed

            assertEquals(expected, actual)
        }
    }
}
