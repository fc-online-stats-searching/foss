package com.foss.foss.feature.matchearching.relative

sealed interface RelativeMatchesEvent {

    object Failed : RelativeMatchesEvent
}
