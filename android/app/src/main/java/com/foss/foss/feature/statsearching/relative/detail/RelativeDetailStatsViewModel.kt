package com.foss.foss.feature.statsearching.relative.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.repository.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RelativeDetailStatsViewModel(
    private val matchRepository: MatchRepository,
) : ViewModel() {
    private val _matches: MutableStateFlow<List<MatchUiModel>> = MutableStateFlow(emptyList())
    val matches: SharedFlow<List<MatchUiModel>>
        get() = _matches.asSharedFlow()

    fun fetchMatchesBetweenUsers(nickname: String, opponentNickname: String) {
        viewModelScope.launch {
            matchRepository.fetchMatchesBetweenUsers(nickname, opponentNickname)
                .onSuccess {
                    _matches.value = it.map { it.toUiModel() }
                }
                .onFailure { }
        }
    }
}
