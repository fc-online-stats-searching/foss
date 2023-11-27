package com.foss.foss.data.legacy.entity.request

data class MatchesIdRequest(
    val userAccessId: String,
    val matchType: Int,
    val offset: Int = DEFAULT_OFFSET,
    val limit: Int = DEFAULT_LIMIT
) {

    companion object {

        private const val DEFAULT_OFFSET = 0
        private const val DEFAULT_LIMIT = 20
    }
}
