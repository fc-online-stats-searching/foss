package com.foss.foss.data

import retrofit2.Response

sealed class HttpError(
    val code: Int,
    override val message: String
) : Throwable(message) {

    class BadRequest(
        response: Response<*>
    ) : HttpError(response.code(), response.message())

    class EmptyBody(
        response: Response<*>
    ) : HttpError(response.code(), "")

    class Unknown(
        response: Response<*>
    ) : HttpError(response.code(), "${response.code()} : ${response.body()}")
}
