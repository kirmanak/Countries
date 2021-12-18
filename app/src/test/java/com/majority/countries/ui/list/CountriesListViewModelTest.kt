package com.majority.countries.ui.list

import com.majority.countries.BaseTest
import com.majority.countries.data.CountriesRepo
import com.majority.countries.data.impl.testSuccessfulCountriesData
import com.majority.countries.data.impl.testSuccessfulCountryListItems
import com.majority.countries.di.DataModule
import com.majority.countries.ui.UiState
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.UnknownHostException

@HiltAndroidTest
@UninstallModules(DataModule::class)
class CountriesListViewModelTest : BaseTest() {
    @BindValue
    @MockK
    lateinit var countriesRepo: CountriesRepo

    @Test
    fun `when repo throws exception then ui state is error`() {
        val err = UnknownHostException("Can't find host")
        coEvery { countriesRepo.getAllCountries() } throws err
        val subject = buildCountriesListViewModel()
        assertEquals(UiState.Error<List<CountryListItem>>(err), subject.uiState.value)
    }

    @Test
    fun `when repo returns data then ui state is content`() {
        val content = testSuccessfulCountryListItems()
        coEvery { countriesRepo.getAllCountries() } returns testSuccessfulCountriesData()
        val subject = buildCountriesListViewModel()
        assertEquals(UiState.Content(content), subject.uiState.value)
    }

    @Test
    fun `when repo is loading for a long time then ui state is loading`() {
        coEvery { countriesRepo.getAllCountries() } coAnswers { delay(1000); emptyList() }
        val subject = buildCountriesListViewModel()
        assertTrue(subject.uiState.value is UiState.Loading)
    }

    @Test
    fun `when try again after request all then requests all`() {
        coEvery { countriesRepo.getAllCountries() } throws UnknownHostException("Can't find host")
        val subject = buildCountriesListViewModel() // Constructor calls requestAll
        subject.tryAgainLastRequest()
        coVerifySequence {
            countriesRepo.getAllCountries()
            countriesRepo.getAllCountries()
        }
        confirmVerified(countriesRepo)
    }

    @Test
    fun `when try again after search by name then searches same name`() {
        coEvery { countriesRepo.getAllCountries() } throws UnknownHostException("Can't find host")
        coEvery { countriesRepo.searchByCountryName(any()) } throws UnknownHostException("Can't find host")
        val subject = buildCountriesListViewModel() // Constructor calls requestAll
        val countryName = "Sweden"
        subject.searchByName(countryName)
        subject.tryAgainLastRequest()
        coVerifySequence {
            countriesRepo.getAllCountries()
            countriesRepo.searchByCountryName(countryName)
            countriesRepo.searchByCountryName(countryName)
        }
        confirmVerified(countriesRepo)
    }

    @Test
    fun `when try again after two searches by name then searches correct name`() {
        coEvery { countriesRepo.getAllCountries() } throws UnknownHostException("Can't find host")
        coEvery { countriesRepo.searchByCountryName(any()) } throws UnknownHostException("Can't find host")
        val subject = buildCountriesListViewModel() // Constructor calls requestAll
        val countryName = "Sweden"
        val secondName = "Russia"
        subject.searchByName(countryName)
        subject.searchByName(secondName)
        subject.tryAgainLastRequest()
        coVerifySequence {
            countriesRepo.getAllCountries()
            countriesRepo.searchByCountryName(countryName)
            countriesRepo.searchByCountryName(secondName)
            countriesRepo.searchByCountryName(secondName)
        }
        confirmVerified(countriesRepo)
    }

    private fun buildCountriesListViewModel() = CountriesListViewModel(countriesRepo)
}
