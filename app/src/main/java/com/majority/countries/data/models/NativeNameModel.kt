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

@Serializable(NativeNameModelSerializer::class)
data class NativeNameModel(
    val languagesToNames: Map<String, NativeNameLanguageModel>,
)

object NativeNameModelSerializer : KSerializer<NativeNameModel> {
    override fun deserialize(decoder: Decoder): NativeNameModel {
        val input = checkNotNull(decoder as? JsonDecoder) { "Decoder must be JSON!" }
        val json = input.decodeJsonElement().jsonObject
        val map = json.entries.map {
            val langModel: NativeNameLanguageModel = input.json.decodeFromJsonElement(it.value)
            it.key to langModel
        }.toMap()
        return NativeNameModel(map)
    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("NativeNameModel") {
        element<Map<String, NativeNameLanguageModel>>("languagesToNames")
    }

    override fun serialize(encoder: Encoder, value: NativeNameModel) {
        TODO("Not yet implemented")
    }
}
