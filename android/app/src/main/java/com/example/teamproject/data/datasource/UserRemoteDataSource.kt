package com.example.teamproject.data.datasource

import com.example.teamproject.data.HttpError
import com.example.teamproject.data.entity.request.MaxRankRequest
import com.example.teamproject.data.entity.request.UserRequest
import com.example.teamproject.data.entity.response.MaxRankResponse
import com.example.teamproject.data.entity.response.UserResponse
import com.example.teamproject.data.service.UserService

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
