package com.majority.countries.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.majority.countries.databinding.VhCountryBinding
import timber.log.Timber

class CountryListItemHolder(
    private val binding: VhCountryBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(countryListItem: CountryListItem) = with(binding) {
        Timber.v("bind() called with $countryListItem")
        capital.text = countryListItem.capital
        currency.text = countryListItem.currency
        name.text = countryListItem.name
        region.text = countryListItem.region
    }
}
