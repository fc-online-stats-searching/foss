package com.foss.foss.data.datasource

import com.foss.foss.data.HttpError
import com.foss.foss.data.entity.request.MaxRankRequest
import com.foss.foss.data.entity.request.UserRequest
import com.foss.foss.data.entity.response.MaxRankResponse
import com.foss.foss.data.entity.response.UserResponse
import com.foss.foss.data.service.UserService

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
