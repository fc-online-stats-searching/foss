package com.foss.foss.feature.matchsearching.relative

sealed interface RelativeMatchEvent {

    object Failed : RelativeMatchEvent
}
