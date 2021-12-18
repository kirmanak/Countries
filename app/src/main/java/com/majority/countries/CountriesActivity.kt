package com.majority.countries

import android.app.SearchManager
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.majority.countries.ui.list.CountriesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CountriesActivity : AppCompatActivity() {

    private val countriesListViewModel: CountriesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("onCreate() called with: savedInstanceState = $savedInstanceState")
        setContentView(R.layout.ac_countries)
    }

    private fun searchCountryByName(name: String) {
        Timber.v("searchCountryByName() called with: name = $name")
        countriesListViewModel.searchByName(name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Timber.v("onCreateOptionsMenu() called with: menu = $menu")
        menuInflater.inflate(R.menu.menu_countries, menu)
        setupSearchView(menu)
        return true
    }

    private fun setupSearchView(menu: Menu?) {
        Timber.v("setupSearchView() called with: menu = $menu")
        val searchView = menu?.findItem(R.id.search)?.actionView as? SearchView ?: return
        val searchManager = getSystemService(this, SearchManager::class.java)
        searchManager?.let { searchView.setSearchableInfo(it.getSearchableInfo(componentName)) }
        searchView.setOnCloseListener {
            onSearchClosed()
            false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.v("onQueryTextSubmit() called with: query = $query")
                query?.let { searchCountryByName(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    private fun onSearchClosed() {
        Timber.v("onSearchClosed() called")
        countriesListViewModel.requestAllCountries()
    }
}