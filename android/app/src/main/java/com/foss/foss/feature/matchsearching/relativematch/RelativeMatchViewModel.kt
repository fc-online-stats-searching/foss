package com.foss.foss.feature.matchsearching.relativematch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.RelativeMatchMapper.toUiModel
import com.foss.foss.repository.RelativeMatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RelativeMatchViewModel @Inject constructor(
    private val relativeMatchRepository: RelativeMatchRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<RelativeMatchUiState> =
        MutableStateFlow(RelativeMatchUiState.Default)
    val uiState: StateFlow<RelativeMatchUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<RelativeMatchEvent> = MutableSharedFlow()
    val event: SharedFlow<RelativeMatchEvent>
        get() = _event.asSharedFlow()

    fun fetchRelativeMatches(nickname: String) {
        viewModelScope.launch {
            flow {
                emit(relativeMatchRepository.fetchRelativeMatches(nickname))
            }.onStart {
                _uiState.value = RelativeMatchUiState.Loading
            }.catch {
                _event.emit(RelativeMatchEvent.Failed)
                _uiState.value = RelativeMatchUiState.Default
            }.collect { relativeMatches ->
                _uiState.value = if (relativeMatches.isEmpty()) {
                    RelativeMatchUiState.Empty
                } else {
                    RelativeMatchUiState.RelativeMatches(relativeMatches.map { it.toUiModel() })
                }
            }
        }
    }

    fun refreshMatches(nickname: String) {
        viewModelScope.launch {
            flow {
                emit(relativeMatchRepository.requestRefresh(nickname))
            }.onStart {
                _uiState.value = RelativeMatchUiState.Loading
            }.catch {
                _event.emit(RelativeMatchEvent.RefreshFailed)
            }.onCompletion {
                _uiState.value = RelativeMatchUiState.Default
            }.collect {
                _event.emit(RelativeMatchEvent.RefreshSucceed)
            }
        }
    }
}
