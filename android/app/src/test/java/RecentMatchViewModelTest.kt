import app.cash.turbine.test
import com.foss.foss.feature.matchsearching.recentmatch.RecentMatchEvent
import com.foss.foss.feature.matchsearching.recentmatch.RecentMatchUiState
import com.foss.foss.feature.matchsearching.recentmatch.RecentMatchViewModel
import com.foss.foss.model.Match
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchType
import com.foss.foss.model.MatchTypeUiModel
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

class RecentMatchViewModelTest {

    private lateinit var matchRepository: MatchRepository
    private lateinit var recentMatchViewModel: RecentMatchViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        matchRepository = mockk()
        recentMatchViewModel = RecentMatchViewModel(matchRepository)
    }

    fun `최근전적 기록을 요청하면`() {
        recentMatchViewModel.fetchMatches("이름", MatchTypeUiModel.ALL)
    }

    fun `최근전적 기록 요청에 대한 결과가 다음과 같을 때`(
        matches: List<Match> = MatchFixture.create(),
        error: Throwable? = null
    ) {
        error?.let {
            coEvery {
                matchRepository.fetchMatches("이름", MatchType.ALL)
            } throws error
        } ?: (
            coEvery {
                matchRepository.fetchMatches("이름", MatchType.ALL)
            } returns matches
            )
    }

    fun `전적 갱신 요청이 성공할 때`() {
        coEvery {
            recentMatchViewModel.refreshMatches("이름")
        } returns Unit
    }

    fun `전적 갱신 요청이 실패할 때`() {
        coEvery {
            recentMatchViewModel.refreshMatches("이름")
        } throws Throwable()
    }

    fun `전적 갱신 요청을 하면`() {
        recentMatchViewModel.refreshMatches("이름")
    }

    @Test
    fun `경기정보 가져오기 성공 시 UiState를 RecentMatches로 업데이트한다`() {
        // given
        val matches = MatchFixture.create()

        `최근전적 기록 요청에 대한 결과가 다음과 같을 때`(matches)

        // when
        `최근전적 기록을 요청하면`()

        val actual = recentMatchViewModel.uiState.value

        // then
        val expected = RecentMatchUiState.RecentMatch(matches.map { it.toUiModel() })

        assertEquals(expected, actual)
    }

    @Test
    fun `매치정보 가져오기 실패 시 Failed 이벤트가 발생한다`() = runTest {
        // given
        `최근전적 기록 요청에 대한 결과가 다음과 같을 때`(error = Throwable())

        recentMatchViewModel.event.test {
            // when
            `최근전적 기록을 요청하면`()

            val actual = awaitItem()

            // then
            val expected = RecentMatchEvent.Failed

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `매치정보 가져오기 실패 시 uiState는 Default가 된다`() = runTest {
        // given
        `최근전적 기록 요청에 대한 결과가 다음과 같을 때`(error = Throwable())

        // when
        `최근전적 기록을 요청하면`()

        val actual = recentMatchViewModel.uiState.value

        // then
        val expected = RecentMatchUiState.Default

        assertEquals(expected, actual)
    }

    @Test
    fun `최근 기록된 전적이 없는 경우 uiState는 Empty가 된다`() = runTest {
        // given
        `최근전적 기록 요청에 대한 결과가 다음과 같을 때`(matches = emptyList())

        // when
        `최근전적 기록을 요청하면`()

        val actual = recentMatchViewModel.uiState.value

        // then
        val expected = RecentMatchUiState.Empty

        assertEquals(expected, actual)
    }

    @Test
    fun `전적 갱신이 성공한 경우 RefreshSucceed 이벤트가 발생한다`() = runTest {
        // given
        `전적 갱신 요청이 성공할 때`()

        recentMatchViewModel.event.test {
            // when
            `전적 갱신 요청을 하면`()

            val actual = awaitItem()

            // then
            val expected = RecentMatchEvent.RefreshSucceed

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `전적 갱신이 실패한 경우 RefreshFailed 이벤트가 발생한다`() = runTest {
        // given
        `전적 갱신 요청이 실패할 때`()

        recentMatchViewModel.event.test {
            // when
            `전적 갱신 요청을 하면`()

            val actual = awaitItem()

            // then
            val expected = RecentMatchEvent.RefreshFailed

            assertEquals(expected, actual)
        }
    }
}
