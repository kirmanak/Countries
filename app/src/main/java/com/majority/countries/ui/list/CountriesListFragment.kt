package com.majority.countries.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majority.countries.R
import com.majority.countries.databinding.FmtCountriesListBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CountriesListFragment : Fragment(R.layout.fmt_countries_list) {

    private val viewModel: CountriesListViewModel by viewModels()
    private val binding: FmtCountriesListBinding by viewBinding(FmtCountriesListBinding::bind)
    private val adapter = CountriesListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v("onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState")
        binding.list.adapter = adapter
        viewModel.requestCountryListItems().observe(viewLifecycleOwner) {
            Timber.d("onViewCreated: received info: $it")
            adapter.submitList(it.getOrDefault(emptyList()))
        }
    }
}