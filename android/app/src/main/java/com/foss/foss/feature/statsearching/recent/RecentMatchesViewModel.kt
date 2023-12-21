package com.foss.foss.feature.statsearching.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchType
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.repository.MatchRepository
import com.foss.foss.util.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RecentMatchesViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {


    private val _matchTypes: MutableLiveData<List<MatchTypeUiModel>> =
        MutableLiveData(MatchType.values().toList().map { machType ->
            machType.toUiModel()
        })

    val matchTypes: LiveData<List<MatchTypeUiModel>>
        get() = _matchTypes

    private val _uiState: MutableStateFlow<UiState<List<MatchUiModel>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<MatchUiModel>>>
        get() = _uiState

    private val _event: MutableSharedFlow<RecentMatchesEvent> =
        MutableSharedFlow()
    val event: SharedFlow<RecentMatchesEvent>
        get() = _event.asSharedFlow()

    fun fetchMatches(nickname: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            matchRepository.fetchMatches(nickname)
                .map { matchResults ->
                    matchResults.map { match ->
                        match.toUiModel()
                    }
                }
                .onSuccess { matchResults ->
                    _uiState.value = UiState.Success(matchResults)
                }.onFailure { error ->
                    _uiState.value = UiState.Error
                    _event.emit(RecentMatchesEvent.Failed)
                }
        }
    }
}