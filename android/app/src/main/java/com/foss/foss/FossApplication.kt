package com.foss.foss

import com.boogiwoogi.woogidi.application.DiApplication
import com.boogiwoogi.woogidi.pure.Instance
import com.foss.foss.data.repository.DefaultMatchRepository
import com.foss.foss.data.repository.FakeRelativeMatchRepository
import com.foss.foss.di.auto.ServiceModule
import com.foss.foss.repository.MatchRepository
import com.foss.foss.repository.RelativeMatchRepository

class FossApplication : DiApplication() {

    override fun onCreate() {
        super.onCreate()

        with(injector) {
            applicationContainer.add(
                Instance<RelativeMatchRepository>(
                    FakeRelativeMatchRepository(),
                ),
            )

            applicationContainer.add(
                Instance<MatchRepository>(
                    DefaultMatchRepository(
                        inject(module = ServiceModule),
                    ),
                ),
            )
        }
    }
}
