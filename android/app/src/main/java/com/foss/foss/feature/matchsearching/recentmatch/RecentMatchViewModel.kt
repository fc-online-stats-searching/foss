package com.foss.foss.feature.matchsearching.recentmatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toDomainModel
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.repository.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentMatchViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RecentMatchUiState> =
        MutableStateFlow(RecentMatchUiState.Default)
    val uiState: StateFlow<RecentMatchUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<RecentMatchEvent> = MutableSharedFlow()
    val event: SharedFlow<RecentMatchEvent>
        get() = _event.asSharedFlow()

    fun fetchMatches(
        nickname: String,
        searchingMatchType: MatchTypeUiModel
    ) {
        viewModelScope.launch {
            flow {
                emit(matchRepository.fetchMatches(nickname, searchingMatchType.toDomainModel()))
            }.onStart {
                _uiState.value = RecentMatchUiState.Loading
            }.catch {
                _event.emit(RecentMatchEvent.Failed)
                _uiState.value = RecentMatchUiState.Default
            }.map { matches ->
                if (matches.isNotEmpty()) {
                    RecentMatchUiState.RecentMatch(matches.map { it.toUiModel() })
                } else {
                    RecentMatchUiState.Empty
                }
            }.collect { uiState ->
                _uiState.value = uiState
            }
        }
    }

    fun refreshMatches(nickname: String) {
        viewModelScope.launch {
            flow {
                emit(matchRepository.requestRefresh(nickname))
            }.onStart {
                _uiState.value = RecentMatchUiState.Loading
            }.catch {
                _event.emit(RecentMatchEvent.RefreshFailed)
            }.onCompletion {
                _uiState.value = RecentMatchUiState.Default
            }.collect {
                _event.emit(RecentMatchEvent.RefreshSucceed)
            }
        }
    }
}
