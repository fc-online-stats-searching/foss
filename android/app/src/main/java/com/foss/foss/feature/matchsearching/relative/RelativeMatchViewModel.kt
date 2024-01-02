package com.foss.foss.feature.matchsearching.relative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.Nickname
import com.foss.foss.model.RelativeMatchMapper.toUiModel
import com.foss.foss.model.RelativeMatchUiModel
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

    private lateinit var _nickname: Nickname
    private lateinit var _opponentName: Nickname

    private val _relativeMatches: MutableStateFlow<List<RelativeMatchUiModel>> =
        MutableStateFlow(emptyList())
    val relativeMatches: StateFlow<List<RelativeMatchUiModel>>
        get() = _relativeMatches.asStateFlow()

    private val _relativeMatchesDetails: MutableStateFlow<List<MatchUiModel>> =
        MutableStateFlow(emptyList())
    val relativeMatchesDetails: StateFlow<List<MatchUiModel>>
        get() = _relativeMatchesDetails.asStateFlow()

    private val _event: MutableSharedFlow<RelativeMatchEvent> = MutableSharedFlow()
    val event: SharedFlow<RelativeMatchEvent>
        get() = _event.asSharedFlow()

    fun fetchRelativeMatches(nickname: String) {
        runCatching {
            _nickname = Nickname(nickname)
            viewModelScope.launch {
                relativeMatchRepository.fetchRelativeMatches(_nickname)
                    .onSuccess { relativeMatches ->
                        _relativeMatches.value = relativeMatches.map { it.toUiModel() }
                    }.onFailure {
                        _event.emit(RelativeMatchEvent.Failed)
                    }
            }
            // TODO: UiState로 적용하면서 닉네임 변환 실패 시 처리 필요
        }.onFailure { }
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
        _opponentName = Nickname(opponentNickname)
    }
}
