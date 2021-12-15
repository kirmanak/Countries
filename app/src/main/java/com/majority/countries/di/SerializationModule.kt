package com.majority.countries.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerializationModule {
    @Provides
    @Singleton
    fun provideJson(): Json {
        Timber.v("provideJson() called")
        return Json { ignoreUnknownKeys = true }
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideConverterFactory(json: Json): Converter.Factory {
        Timber.v("provideConverterFactory() called with: json = $json")
        return json.asConverterFactory("application/json".toMediaType())
    }
}