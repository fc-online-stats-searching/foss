package com.foss.foss.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.FcEventMapper.toUiModel
import com.foss.foss.model.FcEventUiModel
import com.foss.foss.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _fcEvents: MutableStateFlow<List<FcEventUiModel>> = MutableStateFlow(listOf())
    val fcEvents: StateFlow<List<FcEventUiModel>>
        get() = _fcEvents.asStateFlow()

    init {
        viewModelScope.launch {
            flow {
                emit(eventRepository.fetchEvents())
            }.map { events ->
                events.map { it.toUiModel() }
            }.collect { events ->
                _fcEvents.value = events
            }
        }
    }
}
