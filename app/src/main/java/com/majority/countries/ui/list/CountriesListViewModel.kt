package com.majority.countries.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majority.countries.data.CountriesRepo
import com.majority.countries.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountriesListViewModel @Inject constructor(
    private val repo: CountriesRepo,
) : ViewModel() {

    private val mutableUiState = MutableLiveData<UiState<List<CountryListItem>>>(UiState.Loading())
    val uiState: LiveData<UiState<List<CountryListItem>>> by ::mutableUiState

    init {
        requestCountries()
    }

    fun requestCountries() {
        Timber.v("requestCountries() called")
        mutableUiState.value = UiState.Loading()
        viewModelScope.launch {
            mutableUiState.value = try {
                val data = repo.getAllCountries().map { CountryListItem(it) }
                UiState.Content(data)
            } catch (e: Throwable) {
                Timber.e(e, "requestCountries: can't load data")
                if (e is CancellationException) throw e
                else UiState.Error(e)
            }
        }
    }
}