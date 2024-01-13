package com.foss.foss.di.auto

import com.boogiwoogi.woogidi.pure.DefaultModule
import com.boogiwoogi.woogidi.pure.Provides
import com.boogiwoogi.woogidi.pure.Singleton
import com.foss.foss.BuildConfig
import com.foss.foss.data.service.RelativeMatchService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteDataSourceModule : DefaultModule() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesRelativeMatchService(): RelativeMatchService {
        return retrofit.create(RelativeMatchService::class.java)
    }

//    private val interceptor by lazy {
//        Interceptor { chain ->
//            chain.proceed(
//                chain.request()
//                    .newBuilder()
//                    .addHeader(
//                        "Authorization",
//                        BuildConfig.AUTHORIZATION_KEY
//                    ).build()
//            )
//        }
//    }
//
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.SERVER_URL)
//            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
//            .client(
//                OkHttpClient()
//                    .newBuilder()
//                    .addInterceptor(interceptor)
//                    .build()
//            )
//            .build()
//    }

//    @Provides
//    @Singleton
//    fun providesUserService(): UserService {
//        return retrofit.create(UserService::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun providesMatchService(): MatchService {
//        return retrofit.create(MatchService::class.java)
//    }
}
