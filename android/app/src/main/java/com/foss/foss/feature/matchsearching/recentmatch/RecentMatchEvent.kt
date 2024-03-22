package com.foss.foss.feature.matchsearching.recentmatch

sealed interface RecentMatchEvent {

    object None : RecentMatchEvent

    object Failed : RecentMatchEvent

    object RefreshFailed : RecentMatchEvent

    object RefreshSucceed : RecentMatchEvent
}
