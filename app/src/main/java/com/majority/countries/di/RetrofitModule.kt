package com.majority.countries.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(retrofitFactory: RetrofitFactory): Retrofit {
        Timber.v("provideRetrofit() called with: retrofitFactory = $retrofitFactory")
        return retrofitFactory.createRetrofit("https://restcountries.com/")
    }
}