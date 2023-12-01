package com.foss.foss

import com.boogiwoogi.woogidi.application.DiApplication

class FossApplication : DiApplication() {

    override fun onCreate() {
        super.onCreate()

//        with(injector) {
//            applicationContainer.add(
//                Instance<UserRepository>(
//                    DefaultUserRepository(
//                        inject(module = RemoteDataSourceModule)
//                    )
//                )
//            )
//
//            applicationContainer.add(
//                Instance<MatchRepository>(
//                    DefaultMatchRepository(
//                        inject(module = RemoteDataSourceModule)
//                    )
//                )
//            )
    }
}
