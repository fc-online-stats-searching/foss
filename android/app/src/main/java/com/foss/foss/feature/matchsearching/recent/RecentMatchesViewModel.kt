package com.foss.foss.feature.matchsearching.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toDomainModel
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.Nickname
import com.foss.foss.repository.MatchRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecentMatchesViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RecentMatchesUiState> =
        MutableStateFlow(RecentMatchesUiState.Empty)
    val uiState: StateFlow<RecentMatchesUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<RecentMatchesEvent> = MutableSharedFlow()
    val event: SharedFlow<RecentMatchesEvent>
        get() = _event.asSharedFlow()

    fun fetchEmptyMatches() {
        _uiState.value = RecentMatchesUiState.Empty
    }

    fun fetchMatches(
        nickname: String,
        searchingMatchType: MatchTypeUiModel = MatchTypeUiModel.OFFICIAL
    ) {
        viewModelScope.launch {
            runCatching {
                _uiState.value = RecentMatchesUiState.Loading

                matchRepository.fetchMatches(
                    nickname = Nickname(nickname),
                    matchType = searchingMatchType.toDomainModel()
                ).map { matchResults ->
                    matchResults.map { match ->
                        match.toUiModel()
                    }
                }.onSuccess { matchResults ->
                    _uiState.value = RecentMatchesUiState.RecentMatches(matchResults)
                }.onFailure {
                    _event.emit(RecentMatchesEvent.Failed)
                }
            }.onFailure {
                _event.emit(RecentMatchesEvent.Failed)
            }
        }
    }
}
