package com.foss.foss.feature.matchsearching.relative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.RelativeMatchMapper.toUiModel
import com.foss.foss.repository.MatchRepository
import com.foss.foss.repository.RelativeMatchRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RelativeMatchViewModel(
    private val matchRepository: MatchRepository,
    private val relativeMatchRepository: RelativeMatchRepository
) : ViewModel() {

    private lateinit var _nickname: String
    private lateinit var _opponentName: String

    private val _uiState: MutableStateFlow<RelativeMatchUiState> =
        MutableStateFlow(RelativeMatchUiState.Empty)
    val uiState: StateFlow<RelativeMatchUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<RelativeMatchEvent> = MutableSharedFlow()
    val event: SharedFlow<RelativeMatchEvent>
        get() = _event.asSharedFlow()

    private val _relativeMatchesDetails: MutableStateFlow<List<MatchUiModel>> =
        MutableStateFlow(emptyList())
    val relativeMatchesDetails: StateFlow<List<MatchUiModel>>
        get() = _relativeMatchesDetails.asStateFlow()

    fun fetchRelativeMatches(nickname: String) {
        viewModelScope.launch {
            _uiState.value = RelativeMatchUiState.Loading
            relativeMatchRepository.fetchRelativeMatches(nickname)
                .onSuccess { relativeMatches ->
                    _uiState.value = RelativeMatchUiState.RelativeMatches(
                        relativeMatches.map { it.toUiModel() }
                    )
                }.onFailure {
                    _event.emit(RelativeMatchEvent.Failed)
                }
        }
    }

    fun fetchRelativeMatchesBetweenUsers() {
        viewModelScope.launch {
            matchRepository.fetchMatchesBetweenUsers(_nickname, _opponentName)
                .onSuccess { matches ->
                    _relativeMatchesDetails.value = matches.map { it.toUiModel() }
                }
                .onFailure {
                }
        }
    }

    fun resetRelativeMatchesDetails() {
        _relativeMatchesDetails.value = emptyList()
    }

    fun updateOpponentName(opponentNickname: String) {
        _opponentName = opponentNickname
    }
}
