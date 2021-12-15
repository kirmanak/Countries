package com.majority.countries.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyInfoModel(
    val name: String? = null,
    val symbol: String? = null,
)
