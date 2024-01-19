package com.foss.foss.feature.matchsearching.recent

sealed interface RecentMatchEvent {

    object Failed : RecentMatchEvent

    object RefreshFailed : RecentMatchEvent
}
