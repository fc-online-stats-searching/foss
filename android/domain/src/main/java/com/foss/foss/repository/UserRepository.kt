package com.foss.foss.repository

import com.foss.foss.model.Rank
import com.foss.foss.model.User

interface UserRepository {

    suspend fun fetchUser(nickName: String): Result<User>

    suspend fun fetchMaxRank(userAccessId: String): Result<List<Rank>>
}
