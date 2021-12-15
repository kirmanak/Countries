package com.majority.countries.data.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable(CurrencyModelSerializer::class)
data class CurrencyModel(
    val currencyNameToInfo: Map<String, CurrencyInfoModel>,
)

object CurrencyModelSerializer : KSerializer<CurrencyModel> {
    override fun deserialize(decoder: Decoder): CurrencyModel {
        val input = checkNotNull(decoder as? JsonDecoder) { "Decoder must be JSON!" }
        val json = input.decodeJsonElement().jsonObject
        val map = json.entries.map {
            val infoModel: CurrencyInfoModel = input.json.decodeFromJsonElement(it.value)
            it.key to infoModel
        }.toMap()
        return CurrencyModel(map)
    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("CurrencyModel") {
        element<Map<String, CurrencyInfoModel>>("currencyNameToInfo")
    }

    override fun serialize(encoder: Encoder, value: CurrencyModel) {
        TODO("Not yet implemented")
    }
}
