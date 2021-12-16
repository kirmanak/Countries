package com.majority.countries.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NameModel(
    val common: String? = null,
    val official: String? = null,
    val nativeName: Map<String, NativeNameLanguageModel> = emptyMap(),
)
