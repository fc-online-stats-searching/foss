package com.foss.foss.feature.matchsearching.relative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.RelativeMatchMapper.toUiModel
import com.foss.foss.repository.RelativeMatchRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RelativeMatchViewModel(private val relativeMatchRepository: RelativeMatchRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<RelativeMatchUiState> =
        MutableStateFlow(RelativeMatchUiState.Empty)
    val uiState: StateFlow<RelativeMatchUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<RelativeMatchEvent> = MutableSharedFlow()
    val event: SharedFlow<RelativeMatchEvent>
        get() = _event.asSharedFlow()

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
}
