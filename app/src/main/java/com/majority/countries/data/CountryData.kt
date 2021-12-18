package com.majority.countries.data

data class CountryData(
    val pngFlag: String?,
    val svgFlag: String?,
    val startOfWeek: String?,
    val commonName: String?,
    val officialName: String?,
    val nativeOfficialNames: Map<String, String>,
    val nativeCommonNames: Map<String, String>,
    val independent: Boolean?,
    val unMember: Boolean?,
    val status: String?,
    val currencies: List<CurrencyData>,
    val capital: List<String>,
    val region: String?,
    val subregion: String?,
    val languages: Map<String, String>,
    val population: Long?,
)

data class CurrencyData(
    val code: String,
    val name: String,
    val symbol: String,
)
