package com.foss.foss.feature.matchearching.recent

sealed interface RecentMatchesEvent {

    object Failed : RecentMatchesEvent
}
