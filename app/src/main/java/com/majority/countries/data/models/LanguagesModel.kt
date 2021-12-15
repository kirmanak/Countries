package com.majority.countries.data.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(LanguagesModelSerializer::class)
data class LanguagesModel(
    val languageToName: Map<String, String>
)

object LanguagesModelSerializer : KSerializer<LanguagesModel> {
    override fun deserialize(decoder: Decoder): LanguagesModel {
        val input = checkNotNull(decoder as? JsonDecoder) { "Decoder must be JSON!" }
        val json = input.decodeJsonElement().jsonObject
        val map = json.entries.map { it.key to it.value.jsonPrimitive.content }.toMap()
        return LanguagesModel(map)
    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("LanguagesModel") {
        element<Map<String, String>>("languageToName")
    }

    override fun serialize(encoder: Encoder, value: LanguagesModel) {
        TODO("Not yet implemented")
    }
}
