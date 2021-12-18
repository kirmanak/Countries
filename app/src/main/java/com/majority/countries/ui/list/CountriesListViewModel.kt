package com.majority.countries.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majority.countries.data.CountriesRepo
import com.majority.countries.data.CountryData
import com.majority.countries.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

typealias CountryRequest = (suspend () -> List<CountryData>)

@HiltViewModel
class CountriesListViewModel @Inject constructor(
    private val repo: CountriesRepo,
) : ViewModel() {

    init {
        Timber.v("init() called")
    }

    private var lastRequest: CountryRequest? = null
    private val mutableUiState = MutableLiveData<UiState<List<CountryListItem>>>(UiState.Loading())
    val uiState: LiveData<UiState<List<CountryListItem>>> by ::mutableUiState

    init {
        requestAllCountries()
    }

    fun requestAllCountries() {
        Timber.v("requestCountries() called")
        requestCountries { repo.getAllCountries() }
    }

    fun searchByName(name: String) {
        Timber.v("searchByName() called with: name = $name")
        requestCountries { repo.searchByCountryName(name) }
    }

    fun tryAgainLastRequest() {
        Timber.v("tryAgainLastRequest() called")
        val request = checkNotNull(lastRequest) { "lastRequest is null but tryAgain is called" }
        requestCountries(request)
    }

    private fun requestCountries(block: CountryRequest) {
        Timber.v("requestCountries() called")
        lastRequest = block
        mutableUiState.value = UiState.Loading()
        viewModelScope.launch {
            mutableUiState.value = try {
                val data = block().map { CountryListItem(it) }
                UiState.Content(data)
            } catch (e: Throwable) {
                Timber.e(e, "requestCountries: can't load data")
                if (e is CancellationException) throw e
                else UiState.Error(e)
            }
        }
    }
}