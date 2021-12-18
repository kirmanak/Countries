package com.majority.countries.di

import android.content.Context
import com.majority.countries.BuildConfig
import com.majority.countries.ui.ImageLoader
import com.majority.countries.ui.ImageLoaderPicassoImpl
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UiModule {

    companion object {
        @Provides
        @Singleton
        fun providePicasso(
            @ApplicationContext context: Context,
            okHttpClient: OkHttpClient
        ): Picasso {
            Timber.v("providePicasso() called with: context = $context, okHttpClient = $okHttpClient")
            val isDebug = BuildConfig.DEBUG
            return Picasso.Builder(context).apply {
                downloader(OkHttp3Downloader(okHttpClient))
                if (isDebug) {
                    listener { _, uri, exception ->
                        Timber.e(exception, "providePicasso: can't load image from $uri")
                    }
                }
            }.build()
        }
    }

    @Binds
    fun bindImageLoader(picassoImpl: ImageLoaderPicassoImpl): ImageLoader
}