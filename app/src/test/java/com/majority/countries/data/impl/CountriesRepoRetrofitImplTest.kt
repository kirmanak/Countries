package com.majority.countries.data.impl

import com.majority.countries.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import javax.inject.Inject

@HiltAndroidTest
class CountriesRepoRetrofitImplTest : BaseTest() {

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var subject: CountriesRepoRetrofitImpl

    @Test
    fun `when response successful then returns parsed info`() {
        mockWebServer.alwaysRespondWith(testSuccessfulGetAllResponse())
        val actual = runBlocking { subject.getAllCountries() }
        val expected = testSuccessfulCountriesData()
        assertEquals(expected, actual)
    }

    @Test(expected = HttpException::class)
    fun `when response isn't successful then throws error`() {
        mockWebServer.alwaysRespondWith(MockResponse().setResponseCode(404))
        runBlocking { subject.getAllCountries() }
    }
}

fun MockWebServer.alwaysRespondWith(mockResponse: MockResponse) {
    dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse = mockResponse
    }
}