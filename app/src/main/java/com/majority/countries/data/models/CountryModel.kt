package com.majority.countries.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CountryModel(
    val flags: FlagModel,
    val startOfWeek: String? = null,
    val name: NameModel,
    val independent: Boolean? = null,
    val unMember: Boolean? = null,
    val status: String? = null,
    val currencies: CurrencyModel,
    val capital: List<String> = emptyList(),
    val region: String? = null,
    val subregion: String? = null,
    val languages: LanguagesModel,
    val population: Long? = null,
)
