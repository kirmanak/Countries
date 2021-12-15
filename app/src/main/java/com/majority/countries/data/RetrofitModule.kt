package com.majority.countries.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        Timber.v("provideRetrofit() called with: okHttpClient = $okHttpClient")
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://restcountries.com/")
            .build()
    }
}