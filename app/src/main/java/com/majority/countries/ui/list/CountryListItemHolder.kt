package com.majority.countries.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.majority.countries.databinding.VhCountryBinding
import com.majority.countries.ui.ImageLoader
import timber.log.Timber
import javax.inject.Inject

class CountryListItemHolder(
    private val imageLoader: ImageLoader,
    private val binding: VhCountryBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(countryListItem: CountryListItem) = with(binding) {
        Timber.v("bind() called with $countryListItem")
        capital.text = countryListItem.capital
        currency.text = countryListItem.currency
        name.text = countryListItem.name
        region.text = countryListItem.region
        imageLoader.loadImage(flag, countryListItem.pngFlag)
    }

    class Factory @Inject constructor(private val imageLoader: ImageLoader) {
        fun createHolder(binding: VhCountryBinding) = CountryListItemHolder(imageLoader, binding)
    }
}
