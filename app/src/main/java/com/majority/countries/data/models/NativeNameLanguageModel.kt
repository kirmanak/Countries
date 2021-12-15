package com.majority.countries.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NativeNameLanguageModel(
    val official: String? = null,
    val common: String? = null,
)
