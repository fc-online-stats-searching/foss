package com.foss.foss.repository

import com.foss.foss.model.RelativeStat

interface RelativeStatsRepository {

    fun fetchRelativeStats(nickname: String): Result<List<RelativeStat>>
}
