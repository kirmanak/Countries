package com.majority.countries.ui.list

import com.majority.countries.data.CountryData

data class CountryListItem(
    val countryData: CountryData,
) {
    val pngFlag: String? get() = countryData.pngFlag
    val svgFlag: String? get() = countryData.svgFlag
    val name: String? get() = countryData.commonName
    val currency: String? get() = countryData.currencies.firstOrNull()?.name
    val capital: String? get() = countryData.capital.firstOrNull()
    val region: String? get() = countryData.region
}
