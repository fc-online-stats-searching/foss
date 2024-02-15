import app.cash.turbine.test
import com.foss.foss.feature.matchsearching.relative.RelativeMatchEvent
import com.foss.foss.feature.matchsearching.relative.RelativeMatchUiState
import com.foss.foss.feature.matchsearching.relative.RelativeMatchViewModel
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

    fun `전적 갱신 요청이 성공할 때`() {
        coEvery {
            relativeMatchRepository.requestRefresh("이름")
        } returns Unit
    }

    fun `전적 갱신 요청이 실패할 때`() {
        coEvery {
            relativeMatchRepository.requestRefresh("이름")
        } throws Throwable()
    }

    fun `전적 갱신 요청을 하면`() {
        relativeMatchViewModel.refreshMatches("이름")
    }

    @Test
    fun `상대 전적에 대한 데이터를 받아오지 않은 경우 uiState가 Default가 된다`() {
        // given

        // when
        val actual = relativeMatchViewModel.uiState.value

        // then
        val expected = RelativeMatchUiState.Default

        assertEquals(expected, actual)
    }

    @Test
    fun `최근 기록된 전적이 없는 경우 uiState가 Empty가 된다`() {
        // given
        `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.success(emptyList()))

        // when
        `상대 전적 기록 요청을 하면`()

        val actual = relativeMatchViewModel.uiState.value

        // then
        val expected = RelativeMatchUiState.Empty

        assertEquals(expected, actual)
    }

    @Test
    fun `상대 전적을 받아올 때 list가 비어있지 않은 경우 uiState가 RelativeMatches가 된다`() {
        // given
        val relativeMatches = RelativeMatchesFixture.create()

        `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.success(relativeMatches))

        // when
        `상대 전적 기록 요청을 하면`()

        val actual = relativeMatchViewModel.uiState.value

        // then
        val expected = RelativeMatchUiState.RelativeMatches(relativeMatches.map { it.toUiModel() })

        assertEquals(expected, actual)
    }

    @Test
    fun `상대 전적을 받아오는 것에 실패한 경우 Failed 이벤트가 발생한다`() = runTest {
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

    @Test
    fun `상대 전적을 받아오는 것에 실패한 경우 uiState가 Default가 된다`() = runTest {
        // given
        `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.failure(Throwable()))

        // when
        `상대 전적 기록 요청을 하면`()

        val actual = relativeMatchViewModel.uiState.value

        // then
        val expected = RelativeMatchUiState.Default

        assertEquals(expected, actual)
    }

    @Test
    fun `전적 갱신에 실패한 경우 RefreshFailed 이벤트가 발생한다`() = runTest {
        // given
        `전적 갱신 요청이 실패할 때`()

        relativeMatchViewModel.event.test {
            // when
            `전적 갱신 요청을 하면`()

            val actual = awaitItem()

            // then
            val expected = RelativeMatchEvent.RefreshFailed

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `전적 갱신에 실패한 경우 RefreshSucceed 이벤트가 발생한다`() = runTest {
        // given
        `전적 갱신 요청이 성공할 때`()

        relativeMatchViewModel.event.test {
            // when
            `전적 갱신 요청을 하면`()

            val actual = awaitItem()

            // then
            val expected = RelativeMatchEvent.RefreshSucceed

            assertEquals(expected, actual)
        }
    }
}
