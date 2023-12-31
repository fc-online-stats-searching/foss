package com.foss.foss.feature.statsearching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchResult
import com.foss.foss.model.MatchType
import com.foss.foss.model.MatchesResult
import com.foss.foss.model.mapper.MatchMapper.toUiModel
import com.foss.foss.repository.MatchRepository
import com.foss.foss.repository.UserRepository
import com.foss.foss.util.livedata.MutableSingleLiveData
import com.foss.foss.util.livedata.SingleLiveData
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
