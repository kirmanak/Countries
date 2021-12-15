package com.majority.countries.di

import com.majority.countries.data.RestCountriesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestCountriesApiModule {
    @Provides
    @Singleton
    fun provideRestCountriesApi(retrofit: Retrofit): RestCountriesApi {
        Timber.v("provideRestCountriesApi() called with: retrofit = $retrofit")
        return retrofit.create()
    }
}