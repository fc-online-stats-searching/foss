package com.foss.foss.repository.legacy

import com.foss.foss.model.legacy.Rank
import com.foss.foss.model.legacy.User

interface UserRepository {

    suspend fun fetchUser(nickName: String): Result<User>

    suspend fun fetchMaxRank(userAccessId: String): Result<List<Rank>>
}
