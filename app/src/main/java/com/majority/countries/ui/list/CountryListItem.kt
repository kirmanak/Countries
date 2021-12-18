package com.majority.countries.ui.list

import com.majority.countries.data.CountryData

data class CountryListItem(
    val countryData: CountryData,
) {
    val pngFlag: String? get() = countryData.pngFlag
    val name: String? get() = countryData.commonName ?: countryData.officialName
    val capital: String get() = countryData.capital.joinToString(separator = ",")
}
