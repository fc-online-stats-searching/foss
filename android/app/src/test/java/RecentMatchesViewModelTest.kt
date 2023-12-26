import com.foss.foss.feature.statsearching.recent.RecentMatchesViewModel
import com.foss.foss.model.Match
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

    fun `최근전적 기록을 요청하면`(nickname: String) {
        recentMatchesViewModel.fetchMatches(nickname)
    }

    @Test
    fun `경기정보 가져오기 성공 시 UiState를 Success로 업데이트한다`() {
        // given
        val nickname = "사용자 이름"
        val emptyList: List<Match> = listOf()

        coEvery {
            matchRepository.fetchMatches(nickname)
        } returns Result.success(emptyList())

        // when
        `최근전적 기록을 요청하면`(nickname)
        val actual = recentMatchesViewModel.uiState.value

        // then
        val expected = UiState.Success(emptyList)
        assertEquals(expected, actual)
    }

    @Test
    fun `매치정보 가져오기 실패 시 uiState를 Error로 업데이트한다`() {
        // given
        val nickname = "사용자 이름"
        val errorMessage = "Failed"

        coEvery {
            matchRepository.fetchMatches(nickname)
        } returns Result.failure(Throwable(errorMessage))

        // when
        `최근전적 기록을 요청하면`(nickname)
        val actual = recentMatchesViewModel.uiState.value

        // then
        val expected = UiState.Error
        assertEquals(expected, actual)
    }
}
