package com.foss.foss.di.auto

import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Provides
import com.boogiwoogi.woogidi.pure.Singleton
import com.foss.foss.BuildConfig
import com.foss.foss.data.service.RecentMatchService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RemoteDataSource : DefaultModule() {

    private val interceptor by lazy {
        Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(
                        "Authorization",
                        BuildConfig.AUTHORIZATION_KEY,
                    ).build(),
            )
        }
    }

    /**
     * Nexon 에 요청할 때 사용하는 Retrofit
     */
    private val authInterceptorRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .client(
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(interceptor)
                    .build(),
            )
            .build()
    }

    /**
     * 우리 서버에 요청할 때 사용하는 Retrofit
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .build()
    }

//    @Provides
//    @Singleton
//    fun providesUserService(): UserService {
//        return retrofit.create(UserService::class.java)
//    }
//
    @Provides
    @Singleton
    fun providesMatchService(): RecentMatchService {
        return retrofit.create(RecentMatchService::class.java)
    }
}
