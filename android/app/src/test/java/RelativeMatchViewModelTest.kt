import app.cash.turbine.test
import com.foss.foss.feature.matchsearching.relative.RelativeMatchEvent
import com.foss.foss.feature.matchsearching.relative.RelativeMatchUiState
import com.foss.foss.feature.matchsearching.relative.RelativeMatchViewModel
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.RelativeMatch
import com.foss.foss.model.RelativeMatchMapper.toUiModel
import com.foss.foss.repository.MatchRepository
import com.foss.foss.repository.RelativeMatchRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RelativeMatchViewModelTest {

    private lateinit var relativeMatchRepository: RelativeMatchRepository
    private lateinit var matchRepository: MatchRepository
    private lateinit var relativeMatchViewModel: RelativeMatchViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        matchRepository = mockk()
        relativeMatchRepository = mockk()
        relativeMatchViewModel = RelativeMatchViewModel(relativeMatchRepository)
    }

    fun `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(result: Result<List<RelativeMatch>>) {
        coEvery {
            relativeMatchRepository.fetchRelativeMatches(any())
        } returns result
    }

    fun `상대 전적 기록 요청을 하면`() {
        relativeMatchViewModel.fetchRelativeMatches("신공학관캣대디")
    }

    @Test
    fun `상대 전적에 대한 데이터를 받아오지 않은 경우 화면은 비어있는 상태이다`() {
        // given

        // when
        val actual = relativeMatchViewModel.uiState.value

        // then
        val expected = RelativeMatchUiState.Empty

        assertEquals(expected, actual)
    }

    @Test
    fun `상대 전적을 받아온 경우 상대전적 데이터를 보여주고 있는 상태이다`() {
        // given
        val relativeMatches = RelativeMatchesFixture.create()

        `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.success(relativeMatches))

        // when
        `상대 전적 기록 요청을 하면`()

        val actual = relativeMatchViewModel.uiState.value

        // then
        assertTrue(actual is RelativeMatchUiState.RelativeMatches)
    }

    @Test
    fun `상대 전적을 서버로부터 받아와 상대전적 데이터를 보여준다`() {
        // given
        val relativeMatches = RelativeMatchesFixture.create()

        `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.success(relativeMatches))

        // when
        `상대 전적 기록 요청을 하면`()

        val actual =
            (relativeMatchViewModel.uiState.value as? RelativeMatchUiState.RelativeMatches)?.relativeMatches

        // then
        val expected = relativeMatches.map { it.toUiModel() }
        assertEquals(expected, actual)
    }

    @Test
    fun `상대 전적을 받아오는 것에 실패한 경우 실패 이벤트가 발생한다`() = runTest {
        // given
        `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.failure(Throwable()))

        relativeMatchViewModel.event.test {
            // when
            `상대 전적 기록 요청을 하면`()

            val actual = awaitItem()

            // then
            val expected = RelativeMatchEvent.Failed

            assertEquals(expected, actual)
        }
    }
}
