package com.foss.foss

import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Provides
import com.boogiwoogi.woogidi.pure.Singleton
import com.foss.foss.data.service.MatchService
import com.foss.foss.data.service.UserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RemoteDataSourceModule : DefaultModule() {

    private val interceptor by lazy {
        Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(
                        "Authorization",
                        BuildConfig.AUTHORIZATION_KEY
                    ).build()
            )
        }
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .client(
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(interceptor)
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun providesUserService(): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun providesMatchService(): MatchService {
        return retrofit.create(MatchService::class.java)
    }
}
