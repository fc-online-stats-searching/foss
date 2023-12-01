package com.foss.foss.feature.statsearching.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.foss.foss.model.Match
import com.foss.foss.model.MatchMapper.toUiModel
import com.foss.foss.model.MatchType
import com.foss.foss.model.MatchUiModel
import com.foss.foss.repository.MatchRepository

class RecentMatchesViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _matchTypes: MutableLiveData<List<MatchType>> = MutableLiveData(
        MatchType
            .values()
            .toList()
    )
    val matchTypes: LiveData<List<String>>
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

    fun fetchMatches(nickname: String) {
        matchRepository
            .fetchMatches(nickname)
            .onSuccess { matchResults ->
                _matches.value = matchResults
            }.onFailure {}
    }
}
