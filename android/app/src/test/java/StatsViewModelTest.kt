//
// import androidx.arch.core.executor.testing.InstantTaskExecutorRule
// import com.foss.foss.feature.statsearching.legacy.StatsEvent
// import com.foss.foss.feature.statsearching.legacy.StatsUiState
// import com.foss.foss.feature.statsearching.legacy.StatsViewModel
// import com.foss.foss.model.MatchMapper.toUiModel
// import com.foss.foss.model.MatchType
// import com.foss.foss.model.legacy.MatchesResult
// import com.foss.foss.repository.MatchRepository
// import com.foss.foss.repository.legacy.UserRepository
// import io.mockk.coEvery
// import io.mockk.mockk
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.ExperimentalCoroutinesApi
// import kotlinx.coroutines.test.UnconfinedTestDispatcher
// import kotlinx.coroutines.test.setMain
// import org.junit.Assert.assertEquals
// import org.junit.Before
// import org.junit.Rule
// import org.junit.Test
// import java.lang.IllegalArgumentException
//
// class StatsViewModelTest {
//
//    private lateinit var viewModel: StatsViewModel
//    private lateinit var userRepository: UserRepository
//    private lateinit var matchRepository: MatchRepository
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Before
//    fun setUp() {
//        Dispatchers.setMain(UnconfinedTestDispatcher())
//        userRepository = mockk()
//        matchRepository = mockk()
//        viewModel = StatsViewModel(
//            userRepository = userRepository,
//            matchRepository = matchRepository
//        )
//    }
//
//    @Test
//    fun 최근_전적_검색_성공시_경기_결과들을_표시하는_화면상태로_변경된다() {
//        // given
//        val user = UserFixture.createUser()
//        val matchId = MatchFixture.createMatchId()
//        val matchesResult = MatchesResult(listOf(MatchFixture.createMatchResult()))
//
//        coEvery {
//            userRepository.fetchUser("피파온라인유저")
//        } returns Result.success(user)
//
//        coEvery {
//            matchRepository.fetchMatchesId(
//                userAccessId = user.accessId,
//                matchType = MatchType.OFFICIAL
//            )
//        } returns Result.success(matchId)
//
//        coEvery {
//            matchRepository.fetchMatchResult(
//                userAccessId = user.accessId,
//                matchId = matchId.first()
//            )
//        } returns Result.success(matchesResult.value.first())
//
//        // when
//        viewModel.searchRecentlyStats("피파온라인유저")
//        val actual = viewModel.uiState.value
//
//        // then
//        val expected = StatsUiState.Matches(
//            matchesResult = matchesResult
//                .value
//                .map { it.toUiModel() },
//            numberOfWin = matchesResult.numberOfWin,
//            numberOfDraw = matchesResult.numberOfDraw,
//            numberOfLose = matchesResult.numberOfLose,
//            winningRate = matchesResult.winningRate
//        )
//
//        assertEquals(actual, expected)
//    }
//
//    @Test
//    fun 최근_전적_검색_실패시_실패_이벤트가_발생한다() {
//        // given
//        coEvery {
//            userRepository.fetchUser("피파온라인유저")
//        } returns Result.failure(IllegalArgumentException())
//
//        // when
//        viewModel.searchRecentlyStats("피파온라인유저")
//        val actual = viewModel.event.getValue()
//
//        // then
//        val expected = StatsEvent.Failed
//
//        assertEquals(actual, expected)
//    }
// }
