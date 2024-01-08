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

class RecentMatchViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RecentMatchUiState> =
        MutableStateFlow(RecentMatchUiState.Empty)
    val uiState: StateFlow<RecentMatchUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<RecentMatchEvent> = MutableSharedFlow()
    val event: SharedFlow<RecentMatchEvent>
        get() = _event.asSharedFlow()

    fun fetchEmptyMatches() {
        _uiState.value = RecentMatchUiState.Empty
    }

    fun fetchMatches(
        nickname: String,
        searchingMatchType: MatchTypeUiModel = MatchTypeUiModel.OFFICIAL
    ) {
        viewModelScope.launch {
            runCatching {
                _uiState.value = RecentMatchUiState.Loading
                matchRepository.fetchMatches(
                    nickname = Nickname(nickname),
                    matchType = searchingMatchType.toDomainModel()
                ).onSuccess { matchResults ->
                    _uiState.value = RecentMatchUiState.RecentMatch(
                        matchResults.map { matchResult ->
                            matchResult.toUiModel()
                        }
                    )
                }.onFailure {
                    _event.emit(RecentMatchEvent.Failed)
                }
            }.onFailure {
                _event.emit(RecentMatchEvent.Failed)
            }
        }
    }
}
