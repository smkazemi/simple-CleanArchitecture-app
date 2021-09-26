package com.basalam.intern.android

import android.app.Application
import com.basalam.intern.android.di.AppComponent
import com.basalam.intern.android.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }


    }
}