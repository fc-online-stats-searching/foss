package com.foss.foss.data.legacy.datasource

import com.foss.foss.data.legacy.HttpError
import com.foss.foss.data.legacy.entity.request.MaxRankRequest
import com.foss.foss.data.legacy.entity.request.UserRequest
import com.foss.foss.data.legacy.entity.response.MaxRankResponse
import com.foss.foss.data.legacy.entity.response.UserResponse
import com.foss.foss.data.legacy.service.UserService

class UserRemoteDataSource(
    private val userService: UserService
) {

    suspend fun fetchUser(userRequest: UserRequest): UserResponse {
        val response = userService.fetchUser(userRequest.nickName)

        if (response.code() == 200) {
            return response.body() ?: throw HttpError.EmptyBody(response)
        }
        throw HttpError.BadRequest(response)
    }

    suspend fun fetchMaxRank(maxRankRequest: MaxRankRequest): MaxRankResponse {
        val response = userService.fetchMaxRank(maxRankRequest.userAccessId)

        if (response.code() == 200) {
            return response.body() ?: throw HttpError.EmptyBody(response)
        }
        throw HttpError.BadRequest(response)
    }
}
