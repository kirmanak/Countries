package com.majority.countries.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.majority.countries.data.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountriesListViewModel @Inject constructor(
    private val repo: CountriesRepo,
) : ViewModel() {

    fun requestCountryListItems(): LiveData<Result<List<CountryListItem>>> {
        Timber.v("requestInfo() called")
        return liveData {
            val result = runCatching { repo.getAllCountries().map { CountryListItem(it) } }
            Timber.v("requestInfo() returned: $result")
            emit(result)
        }
    }
}