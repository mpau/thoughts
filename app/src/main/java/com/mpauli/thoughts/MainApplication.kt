package com.mpauli.thoughts

import android.app.Application
import com.mpauli.base.baseModule
import com.mpauli.core.apodapi.apodApiModule
import com.mpauli.core.app.appModule
import com.mpauli.core.network.networkModule
import com.mpauli.feature.dayscreen.dayScreenModule
import com.mpauli.feature.overviewscreen.overviewScreenModule
import com.mpauli.feature.perspectivescreen.perspectiveScreenModule
import com.mpauli.feature.thoughtviewer.thoughtViewerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@MainApplication)
            modules(
                appModule,
                apodApiModule,
                baseModule,
                dayScreenModule,
                networkModule,
                overviewScreenModule,
                perspectiveScreenModule,
                thoughtViewerModule,
            )
        }
    }
}
