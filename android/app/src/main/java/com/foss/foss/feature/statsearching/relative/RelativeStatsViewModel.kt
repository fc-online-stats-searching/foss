package com.foss.foss.feature.statsearching.relative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.Nickname
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

    private lateinit var _nickname: Nickname
    private lateinit var _opponentName: Nickname

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

    fun fetchRelativeStats(nickname: String) {
        runCatching {
            _nickname = Nickname(nickname)
            viewModelScope.launch {
                relativeStatsRepository.fetchRelativeStats(_nickname)
                    .onSuccess { relativeStats ->
                        _relativeStats.value = relativeStats.map { it.toUiModel() }
                    }.onFailure {
                        _event.emit(RelativeStatsEvent.Failed)
                    }
            }
            // TODO: UiState로 적용하면서 닉네임 변환 실패 시 처리 필요
        }.onFailure { }
    }

    fun fetchRelativeMatchesBetweenUsers() {
        viewModelScope.launch {
            matchRepository.fetchMatchesBetweenUsers(_nickname, _opponentName)
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
        _opponentName = Nickname(opponentNickname)
    }
}
