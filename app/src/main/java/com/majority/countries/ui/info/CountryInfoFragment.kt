package com.majority.countries.ui.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majority.countries.R
import com.majority.countries.data.CountryData
import com.majority.countries.databinding.FmtCountryInfoBinding
import timber.log.Timber

class CountryInfoFragment : Fragment(R.layout.fmt_country_info) {

    private val binding: FmtCountryInfoBinding by viewBinding(FmtCountryInfoBinding::bind)
    private val args: CountryInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v("onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState")
        binding.setUpViews(args.country)
    }

    private fun FmtCountryInfoBinding.setUpViews(country: CountryData) {
        Timber.v("setUpViews() called with: country = $country")
        name.text = country.officialName
    }
}
