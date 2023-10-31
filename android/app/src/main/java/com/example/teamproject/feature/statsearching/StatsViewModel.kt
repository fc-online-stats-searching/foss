package com.example.teamproject.feature.statsearching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teamproject.model.mapper.MatchMapper.toUiModel
import com.example.teamproject.util.livedata.MutableSingleLiveData
import com.example.teamproject.util.livedata.SingleLiveData
import com.searchingstats.model.MatchResult
import com.searchingstats.model.MatchType
import com.searchingstats.model.MatchesResult
import com.searchingstats.repository.MatchRepository
import com.searchingstats.repository.UserRepository
import kotlinx.coroutines.launch

class StatsViewModel(
    private val userRepository: UserRepository,
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<StatsUiState> = MutableLiveData()
    val uiState: LiveData<StatsUiState>
        get() = _uiState

    private val _event: MutableSingleLiveData<StatsEvent> = MutableSingleLiveData()
    val event: SingleLiveData<StatsEvent>
        get() = _event

    fun searchRecentlyStats(nickname: String) {
        viewModelScope.launch {
            userRepository
                .fetchUser(nickname)
                .onSuccess { user ->
                    searchMatchesId(
                        accessId = user.accessId,
                        matchType = MatchType.OFFICIAL
                    )
                }.onFailure {
                    _event.setValue(StatsEvent.Failed)
                }
        }
    }

    private suspend fun searchMatchesId(
        accessId: String,
        matchType: MatchType
    ) {
        matchRepository.fetchMatchesId(
            userAccessId = accessId,
            matchType = matchType
        ).onSuccess { matchesId ->
            searchMatchResult(
                accessId = accessId,
                matchesId = matchesId
            )
        }.onFailure {
            _event.setValue(StatsEvent.Failed)
        }
    }

    private suspend fun searchMatchResult(
        accessId: String,
        matchesId: List<String>
    ) {
        val results = mutableListOf<MatchResult>()

        matchesId.forEach { matchId ->
            matchRepository
                .fetchMatchResult(
                    userAccessId = accessId,
                    matchId = matchId
                ).onSuccess {
                    results.add(it)
                }
        }
        updateMatchesResult(results)
    }

    private fun updateMatchesResult(results: List<MatchResult>) {
        if (results.isNotEmpty()) {
            with(MatchesResult(results.toList())) {
                _uiState.value = StatsUiState.Matches(
                    matchesResult = value.map { it.toUiModel() },
                    numberOfWin = numberOfWin,
                    numberOfDraw = numberOfDraw,
                    numberOfLose = numberOfLose,
                    winningRate = winningRate
                )
            }
        }
    }
}
