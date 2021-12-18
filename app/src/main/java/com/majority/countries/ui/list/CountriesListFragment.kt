package com.majority.countries.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majority.countries.R
import com.majority.countries.databinding.FmtCountriesListBinding
import com.majority.countries.ui.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
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
        adapter.clicksPublisher.receiveAsFlow().onEach {
            Timber.d("onViewCreated: on item clicked: $it")
            findNavController().navigate(CountriesListFragmentDirections.toCountryInfo(it.countryData))
        }.launchIn(lifecycleScope)
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
            viewModel.requestCountries()
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            Timber.d("setUpViews: new ui state is $it")
            with(progress) {
                isVisible = it is UiState.Loading
                if (it is UiState.Loading) show() else hide()
            }

            errorViews.isVisible = it is UiState.Error
            if (it is UiState.Error) error.text = it.throwable.message

            list.isVisible = it is UiState.Content || it is UiState.Loading
            if (it is UiState.Content) adapter.submitList(it.data) { progress.hide() }
        }
    }
}