package com.foss.foss.feature.matchsearching.relative

sealed interface RelativeMatchesEvent {

    object Failed : RelativeMatchesEvent
}
