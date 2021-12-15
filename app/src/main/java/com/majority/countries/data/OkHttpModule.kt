package com.majority.countries.data

import com.majority.countries.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {
    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        Timber.v("provideOkHttp() called")
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val logger = HttpLoggingInterceptor.Logger { Timber.tag("OkHttp").v(it) }
                val loggingInterceptor = HttpLoggingInterceptor(logger).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addNetworkInterceptor(loggingInterceptor)
            }
        }.build()
    }
}