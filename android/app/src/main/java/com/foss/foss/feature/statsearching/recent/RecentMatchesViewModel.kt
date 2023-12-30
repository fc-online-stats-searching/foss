package com.foss.foss.feature.statsearching.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.foss.foss.model.Match
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchType
import com.foss.foss.model.MatchTypeUiModel
import com.foss.foss.model.MatchUiModel
import com.foss.foss.model.Nickname
import com.foss.foss.repository.MatchRepository
import kotlinx.coroutines.launch

class RecentMatchesViewModel(
    private val matchRepository: MatchRepository,
) : ViewModel() {

    private val _matchTypes: MutableLiveData<List<MatchType>> = MutableLiveData(
        MatchType
            .values()
            .toList(),
    )
    val matchTypes: LiveData<List<MatchTypeUiModel>>
        get() = _matchTypes.map { matchTypes ->
            matchTypes.map { matchType ->
                matchType.toUiModel()
            }
        }

    private val _matches: MutableLiveData<List<Match>> = MutableLiveData()
    val matches: LiveData<List<MatchUiModel>>
        get() = _matches.map { matches ->
            matches.map { match ->
                match.toUiModel()
            }
        }

    private val _event: MutableLiveData<RecentMatchesEvent> = MutableLiveData()
    val event: LiveData<RecentMatchesEvent>
        get() = _event

    fun fetchMatches(_nickname: String) {
        runCatching {
            val nickname = Nickname(_nickname)
            viewModelScope.launch {
                matchRepository.fetchMatches(nickname)
                    .onSuccess { matchResults ->
                        _matches.value = matchResults
                    }.onFailure { }
            }
        }.onFailure { _event.value = RecentMatchesEvent.Failed }
    }
}
