package com.example.teamproject.di

import com.example.teamproject.BuildConfig
import com.example.teamproject.data.datasource.MatchRemoteDataSource
import com.example.teamproject.data.datasource.UserRemoteDataSource
import com.example.teamproject.data.service.MatchService
import com.example.teamproject.data.service.UserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RemoteDataSourceProvider {

    private val interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader(
                    "Authorization",
                    BuildConfig.AUTHORIZATION_KEY
                ).build()
        )
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
        .client(
            OkHttpClient()
                .newBuilder()
                .addInterceptor(interceptor)
                .build()
        )
        .build()

    private val userService: UserService = retrofit.create(UserService::class.java)
    private val matchService: MatchService = retrofit.create(MatchService::class.java)

    val userRemoteDataSource = UserRemoteDataSource(userService)
    val matchRemoteDataSource = MatchRemoteDataSource(matchService)
}
