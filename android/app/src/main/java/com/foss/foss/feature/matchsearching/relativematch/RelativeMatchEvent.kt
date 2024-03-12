package com.foss.foss.feature.matchsearching.relativematch

sealed interface RelativeMatchEvent {

    object Failed : RelativeMatchEvent

    object RefreshFailed : RelativeMatchEvent

    object RefreshSucceed : RelativeMatchEvent
}
