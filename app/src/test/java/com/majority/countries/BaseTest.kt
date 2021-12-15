package com.majority.countries

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import timber.log.Timber

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
abstract class BaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUpHilt() {
        hiltRule.inject()
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun setUpTimber() {
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    println(message)
                    t?.printStackTrace()
                }
            })
        }
    }
}