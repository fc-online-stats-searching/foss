package com.foss.foss.feature.statsearching.legacy

sealed class StatsEvent(
    val message: String
) {

    object Failed : StatsEvent(ERROR_MESSAGE_SEARCHING_FAILED)

    companion object {

        private const val ERROR_MESSAGE_SEARCHING_FAILED: String = "검색에 실패했습니다."
    }
}
