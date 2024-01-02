import app.cash.turbine.test
import com.foss.foss.feature.matchsearching.relative.RelativeMatchEvent
import com.foss.foss.feature.matchsearching.relative.RelativeMatchViewModel
import com.foss.foss.model.Match
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.RelativeMatch
import com.foss.foss.model.RelativeMatchMapper.toUiModel
import com.foss.foss.model.RelativeMatchUiModel
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
        relativeMatchViewModel = RelativeMatchViewModel(matchRepository, relativeMatchRepository)
    }

    fun `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(result: Result<List<RelativeMatch>>) {
        coEvery {
            relativeMatchRepository.fetchRelativeMatches(any())
        } returns result
    }

    fun `특정 유저와의 상대 전적 요청 결과가 다음과 같을 때`(result: Result<List<Match>>) {
        coEvery {
            matchRepository.fetchMatchesBetweenUsers(any(), any())
        } returns result
    }

    fun `특정 유저와의 상대 전적을 초기화 하면`() {
        relativeMatchViewModel.resetRelativeMatchesDetails()
    }

    fun `특정 유저와의 상대 전적 요청을 하면`() {
        relativeMatchViewModel.fetchRelativeMatchesBetweenUsers()
    }

    fun `상대 전적 기록 요청을 하면`() {
        relativeMatchViewModel.fetchRelativeMatches("신공학관캣대디")
    }

    fun `상대방 닉네임을 초기화 하면`() {
        relativeMatchViewModel.updateOpponentName("신공학관캣대디")
    }

    @Test
    fun `상대 전적에 대한 데이터를 받아오지 않은 경우 빈 리스트이다`() {
        // given

        // when
        val actual = relativeMatchViewModel.relativeMatches.value

        // then
        val expected = listOf<RelativeMatchUiModel>()

        assertEquals(expected, actual)
    }

    @Test
    fun `상대 전적을 받아온 경우 빈 리스트가 아니다`() {
        // given
        `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.success(RelativeMatchesFixture.create()))

        // when
        `상대 전적 기록 요청을 하면`()

        val actual = relativeMatchViewModel.relativeMatches.value

        // then
        val expected = RelativeMatchesFixture.create().map { it.toUiModel() }

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

    @Test
    fun `상대 전적을 불러온 뒤 특정 유저와의 전적을 받아온 경우 빈 리스트가 아니다`() {
        // given
        `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.success(RelativeMatchesFixture.create()))
        `특정 유저와의 상대 전적 요청 결과가 다음과 같을 때`(Result.success(MatchFixture.create()))

        // when
        `상대 전적 기록 요청을 하면`()
        `상대방 닉네임을 초기화 하면`()
        `특정 유저와의 상대 전적 요청을 하면`()

        val actual = relativeMatchViewModel.relativeMatchesDetails.value

        // then
        val expected = MatchFixture.create().map { it.toUiModel() }

        assertEquals(expected, actual)
    }

    @Test
    fun `특정 유저와의 상대 전적을 초기화 하면 특정 유저와의 상대 전적을 저장하는 리스트의 길이는 0이다`() {
        // given
        `상대전적 기록 요청에 대한 결과가 다음과 같을 때`(Result.success(RelativeMatchesFixture.create()))
        `특정 유저와의 상대 전적 요청 결과가 다음과 같을 때`(Result.success(MatchFixture.create()))

        // when
        `상대 전적 기록 요청을 하면`()
        `상대방 닉네임을 초기화 하면`()
        `특정 유저와의 상대 전적 요청을 하면`()

        val actual = relativeMatchViewModel.relativeMatchesDetails.value

        // then
        val expected = MatchFixture.create().map { it.toUiModel() }

        assertEquals(expected, actual)

        // and
        `특정 유저와의 상대 전적을 초기화 하면`()

        // then
        val actualAfterReset = relativeMatchViewModel.relativeMatchesDetails.value
        assertEquals(0, actualAfterReset.size)
    }
}
