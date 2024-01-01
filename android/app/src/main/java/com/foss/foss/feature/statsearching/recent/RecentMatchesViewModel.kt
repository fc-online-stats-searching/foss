package com.foss.foss.feature.statsearching.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchType
import com.foss.foss.model.Nickname
import com.foss.foss.repository.MatchRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RecentMatchesViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RecentMatchesUiState> = MutableStateFlow(
        RecentMatchesUiState.Default(MatchType.values().map { it.toUiModel() })
    )
    val uiState: StateFlow<RecentMatchesUiState>
        get() = _uiState

    private val _event: MutableSharedFlow<RecentMatchesEvent> = MutableSharedFlow()
    val event: SharedFlow<RecentMatchesEvent>
        get() = _event.asSharedFlow()

    fun fetchMatches(nickname: String) {
        viewModelScope.launch {
            _uiState.value = RecentMatchesUiState.Loading

            matchRepository.fetchMatches(Nickname(nickname)).map { matchResults ->
                matchResults.map { match ->
                    match.toUiModel()
                }
            }.onSuccess { matchResults ->
                _uiState.value = RecentMatchesUiState.Success(matchResults)
            }.onFailure {
                _event.emit(RecentMatchesEvent.Failed)
            }
        }
    }
}
