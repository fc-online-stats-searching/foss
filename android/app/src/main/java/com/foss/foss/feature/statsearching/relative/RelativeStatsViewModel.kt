package com.foss.foss.feature.statsearching.relative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.RelativeStatMapper.toUiModel
import com.foss.foss.model.RelativeStatUiModel
import com.foss.foss.repository.MatchRepository
import com.foss.foss.repository.RelativeStatsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RelativeStatsViewModel(
    private val matchRepository: MatchRepository,
    private val relativeStatsRepository: RelativeStatsRepository,
) : ViewModel() {

    val name = MutableStateFlow("")
    private var _opponentName = ""

    private val _relativeStats: MutableStateFlow<List<RelativeStatUiModel>> =
        MutableStateFlow(emptyList())
    val relativeStats: StateFlow<List<RelativeStatUiModel>>
        get() = _relativeStats.asStateFlow()

    private val _relativeStatsDetails: MutableStateFlow<List<MatchUiModel>> =
        MutableStateFlow(emptyList())
    val relativeStatsDetails: StateFlow<List<MatchUiModel>>
        get() = _relativeStatsDetails.asStateFlow()

    private val _event: MutableSharedFlow<RelativeStatsEvent> = MutableSharedFlow()
    val event: SharedFlow<RelativeStatsEvent>
        get() = _event.asSharedFlow()

    fun fetchRelativeStats() {
        viewModelScope.launch {
            relativeStatsRepository.fetchRelativeStats(name.value)
                .onSuccess { relativeStats ->
                    _relativeStats.value = relativeStats.map { it.toUiModel() }
                }.onFailure {
                    _event.emit(RelativeStatsEvent.Failed)
                }
        }
    }

    fun fetchMatchesBetweenUsers() {
        viewModelScope.launch {
            matchRepository.fetchMatchesBetweenUsers(name.value, _opponentName)
                .onSuccess { matches ->
                    _relativeStatsDetails.value = matches.map { it.toUiModel() }
                }
                .onFailure { }
        }
    }

    fun resetRelativeStatsDetails() {
        _relativeStatsDetails.value = emptyList()
    }

    fun updateOpponentName(opponentNickname: String) {
        _opponentName = opponentNickname
    }
}
