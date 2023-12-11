package com.foss.foss.feature.statsearching.relative

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.RelativeStatMapper.toUiModel
import com.foss.foss.model.RelativeStatUiModel
import com.foss.foss.repository.RelativeStatsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RelativeStatsViewModel(
    private val relativeStatsRepository: RelativeStatsRepository
) : ViewModel() {

    private val _relativeStats: MutableStateFlow<List<RelativeStatUiModel>> =
        MutableStateFlow(listOf())
    val relativeStats: StateFlow<List<RelativeStatUiModel>>
        get() = _relativeStats.asStateFlow()

    fun fetchRelativeStats(nickname: String) {
        viewModelScope.launch {
            relativeStatsRepository.fetchRelativeStats(nickname)
                .onSuccess { relativeStats ->
                    _relativeStats.value = relativeStats.map { it.toUiModel() }
                }.onFailure {}
        }
    }
}
