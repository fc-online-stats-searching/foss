package com.foss.foss.feature.matchsearching.recent

sealed interface RecentMatchesEvent {

    object Failed : RecentMatchesEvent
}
