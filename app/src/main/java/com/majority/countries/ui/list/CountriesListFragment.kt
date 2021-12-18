package com.majority.countries.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majority.countries.R
import com.majority.countries.databinding.FmtCountriesListBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CountriesListFragment : Fragment(R.layout.fmt_countries_list) {

    @Inject
    lateinit var adapter: CountriesListAdapter
    private val viewModel: CountriesListViewModel by viewModels()
    private val binding: FmtCountriesListBinding by viewBinding(FmtCountriesListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v("onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState")
        binding.setUpViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.v("onDestroyView() called")
        // RV registers itself as observer inside adapter. If it doesn't unregister then RV is leaked
        binding.list.adapter = null
    }

    private fun FmtCountriesListBinding.setUpViews() {
        Timber.v("setUpViews() called")
        list.adapter = adapter
        progress.setVisibilityAfterHide(View.GONE)
        retryButton.setOnClickListener {
            Timber.d("setUpViews: on retry click")
            requestCountryListItems()
        }
        requestCountryListItems()
    }

    private fun FmtCountriesListBinding.requestCountryListItems() {
        Timber.v("requestCountryListItems() called")
        progress.isVisible = true
        progress.show()
        errorViews.isVisible = false
        viewModel.requestCountryListItems().observe(viewLifecycleOwner) { result ->
            Timber.d("requestCountryListItems: received info: $result")
            errorViews.isVisible = result.isFailure
            error.text = result.exceptionOrNull()?.message
            list.isVisible = result.isSuccess
            adapter.submitList(result.getOrDefault(emptyList())) { progress.hide() }
        }
    }
}