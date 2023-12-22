package com.foss.foss.feature.statsearching.recent

sealed interface RecentMatchesEvent {

    object Failed : RecentMatchesEvent
}
