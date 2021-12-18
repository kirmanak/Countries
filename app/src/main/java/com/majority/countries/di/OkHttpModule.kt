package com.majority.countries.di

import android.content.Context
import android.os.StatFs
import com.majority.countries.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {
    @Provides
    @Singleton
    fun provideOkHttp(@ApplicationContext context: Context): OkHttpClient {
        Timber.v("provideOkHttp() called")
        return OkHttpClient.Builder().apply {
            cache(createCache(context))
            if (BuildConfig.DEBUG) {
                val logger = HttpLoggingInterceptor.Logger { Timber.tag("OkHttp").v(it) }
                val loggingInterceptor = HttpLoggingInterceptor(logger).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addNetworkInterceptor(loggingInterceptor)
            }
        }.build()
    }

    private fun createCache(context: Context): Cache {
        Timber.v("createCache() called with: context = $context")
        val cacheDir = File(context.cacheDir, "okhttp").apply {
            if (!exists()) mkdirs()
        }
        val cacheSize = calculateDiskCacheSize(
            dir = cacheDir,
            minSize = 10L * 1024 * 1024,
            maxSize = 1024L * 1024 * 1024,
        )
        Timber.d("provideOkHttp: cache size is $cacheSize")
        return Cache(directory = cacheDir, maxSize = cacheSize)
    }

    // Copied from com.squareup.picasso.Utils.calculateDiskCacheSize
    private fun calculateDiskCacheSize(dir: File, minSize: Long, maxSize: Long): Long {
        val size = try {
            val statFs = StatFs(dir.absolutePath)
            val blockCount = statFs.blockCountLong
            val blockSize = statFs.blockSizeLong
            val available = blockCount * blockSize
            // Target 2% of the total space.
            available / 50
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "calculateDiskCacheSize: can't calculate disk size")
            minSize
        }

        // Bound inside min/max size for disk cache.
        return size.coerceAtMost(maxSize).coerceAtLeast(minSize)
    }
}