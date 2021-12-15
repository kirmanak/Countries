package com.majority.countries.ui.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majority.countries.R
import com.majority.countries.databinding.FmtCountriesListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesListFragment : Fragment(R.layout.fmt_countries_list) {

    private val viewModel: CountriesListViewModel by viewModels()
    private val binding: FmtCountriesListBinding by viewBinding(FmtCountriesListBinding::bind)

}