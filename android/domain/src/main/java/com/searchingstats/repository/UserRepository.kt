package com.searchingstats.repository

import com.searchingstats.model.Rank
import com.searchingstats.model.User

interface UserRepository {

    suspend fun fetchUser(nickName: String): Result<User>

    suspend fun fetchMaxRank(userAccessId: String): Result<List<Rank>>
}
