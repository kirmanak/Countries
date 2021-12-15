package com.majority.countries.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RetrofitModule::class],
)
object FakeRetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(retrofitFactory: RetrofitFactory, mockWebServer: MockWebServer): Retrofit {
        Timber.v("provideRetrofit() called with: retrofitFactory = $retrofitFactory, mockWebServer = $mockWebServer")
        val url = mockWebServer.url("/").toString()
        return retrofitFactory.createRetrofit(url)
    }

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer {
        Timber.v("provideMockWebServer() called")
        return MockWebServer()
    }
}