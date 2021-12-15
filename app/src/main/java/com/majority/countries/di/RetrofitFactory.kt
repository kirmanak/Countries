package com.majority.countries.di

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitFactory @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val converterFactory: Converter.Factory
) {

    fun createRetrofit(baseUrl: String): Retrofit {
        Timber.v("createRetrofit() called with: baseUrl = $baseUrl")
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
    }
}