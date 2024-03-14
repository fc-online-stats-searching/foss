package com.foss.foss.feature.matchsearching.relativematch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.foss.foss.data.datasource.RelativeMatchDataSource
import com.foss.foss.data.repository.DefaultRelativeMatchRepository
import com.foss.foss.data.service.RelativeMatchService
import com.foss.foss.di.auto.RetrofitModule.retrofit
import com.foss.foss.model.RelativeMatchMapper.toUiModel
import com.foss.foss.repository.RelativeMatchRepository
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

class RelativeMatchViewModel(private val relativeMatchRepository: RelativeMatchRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<RelativeMatchUiState> =
        MutableStateFlow(RelativeMatchUiState.Default)
    val uiState: StateFlow<RelativeMatchUiState>
        get() = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<RelativeMatchEvent> = MutableSharedFlow()
    val event: SharedFlow<RelativeMatchEvent>
        get() = _event.asSharedFlow()

    fun fetchDefaultRelativeMatches() {
        _uiState.value = RelativeMatchUiState.Default
    }

    fun fetchRelativeMatches(nickname: String) {
        viewModelScope.launch {
            _uiState.value = RelativeMatchUiState.Loading
            relativeMatchRepository.fetchRelativeMatches(nickname)
                .onSuccess { relativeMatches ->
                    _uiState.value = if (relativeMatches.isEmpty()) {
                        RelativeMatchUiState.Empty
                    } else {
                        RelativeMatchUiState.RelativeMatches(relativeMatches.map { it.toUiModel() })
                    }
                }.onFailure {
                    _event.emit(RelativeMatchEvent.Failed)
                    _uiState.value = RelativeMatchUiState.Default
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

    companion object {
        val Factory = RelativeMatchViewModelFactory(
            DefaultRelativeMatchRepository(
                RelativeMatchDataSource((retrofit.create(RelativeMatchService::class.java))),
            ),
        )

        @Suppress("UNCHECKED_CAST")
        class RelativeMatchViewModelFactory(private val relativeMatchRepository: RelativeMatchRepository) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RelativeMatchViewModel(relativeMatchRepository) as T
            }
        }
    }
}
