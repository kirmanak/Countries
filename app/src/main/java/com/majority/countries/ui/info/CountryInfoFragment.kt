package com.majority.countries.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.majority.countries.R
import com.majority.countries.data.CountryData
import com.majority.countries.databinding.FmtCountryInfoBinding
import com.majority.countries.ui.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.NumberFormat
import javax.inject.Inject

@AndroidEntryPoint
class CountryInfoFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var imageLoader: ImageLoader
    private val binding: FmtCountryInfoBinding by viewBinding(FmtCountryInfoBinding::bind)
    private val args: CountryInfoFragmentArgs by navArgs()
    private val numberFormat by lazy { NumberFormat.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.v("onCreateView() called with: inflater = $inflater, container = $container, savedInstanceState = $savedInstanceState")
        return inflater.inflate(R.layout.fmt_country_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.v("onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState")
        binding.setUpViews(args.country)
    }

    private fun FmtCountryInfoBinding.setUpViews(country: CountryData) {
        Timber.v("setUpViews() called with: country = $country")
        name.text = country.officialName ?: country.commonName
        name.isVisible = name.text.isNotEmpty()

        capital.isVisible = country.capital.isNotEmpty()
        capital.text = resources.getQuantityString(
            R.plurals.fmt_country_info_capital,
            country.capital.size,
            country.capital.joinToString()
        )

        val regionInfo = listOf(country.subregion, country.region).filterNot { it.isNullOrBlank() }
        region.isVisible = regionInfo.isNotEmpty()
        region.text = resources.getString(
            R.string.fmt_country_info_region, regionInfo.joinToString()
        )

        population.isVisible = country.population != null
        country.population?.let {
            population.text = getString(
                R.string.fmt_country_info_population, numberFormat.format(it)
            )
        }

        imageLoader.loadImage(flag, country.pngFlag)
    }
}
