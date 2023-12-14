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

    private val _relativeStats: MutableStateFlow<List<RelativeStatUiModel>> =
        MutableStateFlow(listOf())
    val relativeStats: StateFlow<List<RelativeStatUiModel>>
        get() = _relativeStats.asStateFlow()

    private val _relativeStatsDetails: MutableStateFlow<List<MatchUiModel>> =
        MutableStateFlow(emptyList())
    val relativeStatsDetails: StateFlow<List<MatchUiModel>>
        get() = _relativeStatsDetails.asStateFlow()

    private val _event: MutableSharedFlow<RelativeStatsEvent> = MutableSharedFlow()
    val event: SharedFlow<RelativeStatsEvent>
        get() = _event.asSharedFlow()

    fun fetchRelativeStats(nickname: String) {
        viewModelScope.launch {
            relativeStatsRepository.fetchRelativeStats(nickname)
                .onSuccess { relativeStats ->
                    _relativeStats.value = relativeStats.map { it.toUiModel() }
                }.onFailure {
                    _event.emit(RelativeStatsEvent.Failed)
                }
        }
    }

    fun fetchMatchesBetweenUsers(nickname: String, opponentNickname: String) {
        viewModelScope.launch {
            matchRepository.fetchMatchesBetweenUsers(nickname, opponentNickname)
                .onSuccess { matches ->
                    _relativeStatsDetails.value = matches.map { it.toUiModel() }
                }
                .onFailure { }
        }
    }
}
