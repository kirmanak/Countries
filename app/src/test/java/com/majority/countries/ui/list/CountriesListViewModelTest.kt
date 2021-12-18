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

    private fun buildCountriesListViewModel() = CountriesListViewModel(countriesRepo)
}
