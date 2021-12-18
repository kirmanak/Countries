package com.majority.countries.data.impl

import com.majority.countries.BaseTest
import com.majority.countries.data.CountryData
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

    @Test
    fun `when countries requested then path is correct`() {
        mockWebServer.alwaysRespondWith(testSuccessfulGetAllResponse())
        runBlocking { subject.getAllCountries() }
        assertEquals(
            "/v3.1/all?fields=name%2Cindependent%2Cstatus%2CunMember%2Ccurrencies%2Ccapital%2Cpopulation%2Cflags%2Cregion%2Csubregion%2Clanguages%2CstartOfWeek",
            mockWebServer.takeRequest().path
        )
    }

    @Test
    fun `when search successful then returns parsed info`() {
        mockWebServer.alwaysRespondWith(testSuccessfulGetAllResponse())
        val actual = runBlocking { subject.searchByCountryName("Sweden") }
        val expected = testSuccessfulCountriesData()
        assertEquals(expected, actual)
    }

    @Test(expected = HttpException::class)
    fun `when search isn't successful then throws error`() {
        mockWebServer.alwaysRespondWith(MockResponse().setResponseCode(503))
        runBlocking { subject.searchByCountryName("Sweden") }
    }

    @Test
    fun `when search by name requested then path is correct`() {
        mockWebServer.alwaysRespondWith(testSuccessfulGetAllResponse())
        val countryName = "Sweden"
        runBlocking { subject.searchByCountryName(countryName) }
        assertEquals(
            "/v3.1/name/Sweden?fields=name%2Cindependent%2Cstatus%2CunMember%2Ccurrencies%2Ccapital%2Cpopulation%2Cflags%2Cregion%2Csubregion%2Clanguages%2CstartOfWeek",
            mockWebServer.takeRequest().path
        )
    }

    @Test
    fun `when country not found by name then returns empty list`() {
        mockWebServer.alwaysRespondWith(testCountryNotFoundByName())
        val actual = runBlocking { subject.searchByCountryName("Sweden") }
        assertEquals(emptyList<List<CountryData>>(), actual)
    }
}

fun MockWebServer.alwaysRespondWith(mockResponse: MockResponse) {
    dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse = mockResponse
    }
}