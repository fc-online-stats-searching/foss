package com.foss.foss.repository

import com.foss.foss.model.Nickname
import com.foss.foss.model.RelativeStat

interface RelativeStatsRepository {

    fun fetchRelativeStats(nickname: Nickname): Result<List<RelativeStat>>
}
