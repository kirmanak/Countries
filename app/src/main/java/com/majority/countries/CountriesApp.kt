package com.majority.countries

import android.app.Application
import timber.log.Timber

class CountriesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        Timber.v("onCreate() called")
    }
}