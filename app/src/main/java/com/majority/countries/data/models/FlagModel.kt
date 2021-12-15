package com.majority.countries.data.models

import kotlinx.serialization.Serializable

@Serializable
data class FlagModel(
    val png: String? = null,
    val svg: String? = null,
)
