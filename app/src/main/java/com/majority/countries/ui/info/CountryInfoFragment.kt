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
import com.majority.countries.databinding.ViewCountryInfoTextBinding
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
        imageLoader.loadImage(flag, country.pngFlag)
        name.text = country.officialName ?: country.commonName
        name.isVisible = name.text.isNotEmpty()

        val texts: MutableList<String> = ArrayList()

        country.capitals.filterNot { it.isBlank() }.takeIf { it.isNotEmpty() }?.let {
            val capital = resources.getQuantityString(
                R.plurals.fmt_country_info_capital, it.size, it.joinToString()
            )
            texts.add(capital)
        }

        val regions = listOf(country.subregion, country.region)
        regions.filterNot { it.isNullOrBlank() }.takeIf { it.isNotEmpty() }?.let {
            texts.add(resources.getString(R.string.fmt_country_info_region, it.joinToString()))
        }

        country.population?.let {
            texts.add(getString(R.string.fmt_country_info_population, numberFormat.format(it)))
        }

        country.independent?.let {
            val string =
                if (it) R.string.fmt_country_info_boolean_yes else R.string.fmt_country_info_boolean_no
            texts.add(getString(R.string.fmt_country_info_independent, getString(string)))
        }

        country.startOfWeek?.let {
            texts.add(getString(R.string.fmt_country_info_start_week, it))
        }

        country.unMember?.let {
            val string =
                if (it) R.string.fmt_country_info_boolean_yes else R.string.fmt_country_info_boolean_no
            texts.add(getString(R.string.fmt_country_info_un_member, getString(string)))
        }

        country.currencies
            .takeUnless { it.isEmpty() }
            ?.map { getString(R.string.fmt_country_info_currency, it.code, it.name, it.symbol) }
            ?.let { texts.add(getString(R.string.fmt_country_info_currencies, it.joinToString())) }

        Timber.d("setUpViews: created list of texts: $texts")
        for (content in texts) {
            ViewCountryInfoTextBinding.inflate(layoutInflater, infoHolder, false).root.apply {
                text = content
                id = View.generateViewId()
                infoHolder.addView(this)
                textsFlow.addView(this)
            }
        }
    }
}
