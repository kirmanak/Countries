package com.majority.countries.data.impl

import com.majority.countries.data.CountryData
import com.majority.countries.data.CurrencyData
import com.majority.countries.ui.list.CountryListItem
import okhttp3.mockwebserver.MockResponse
import org.intellij.lang.annotations.Language

@Language("JSON")
fun testSuccessfulGetAllResponse() = MockResponse()
    .setResponseCode(200)
    .setBody(
        """
            [
    {
        "flags": {
            "png": "https://flagcdn.com/w320/mt.png"
        },
        "startOfWeek": "monday",
        "name": {
            "official": "Republic of Malta",
            "nativeName": {
                "eng": {
                    "common": "Malta"
                },
                "mlt": {
                    "official": "Repubblika ta ' Malta",
                    "common": "Malta"
                }
            }
        },
        "independent": true,
        "status": "officially-assigned",
        "currencies": {
            "EUR": {
                "name": "Euro",
                "symbol": "€"
            }
        },
        "region": "Europe",
        "subregion": "Southern Europe",
        "languages": {
            "eng": "English",
            "mlt": "Maltese"
        },
        "population": 525285
    },
    {
        "flags": {
            "png": "https://flagcdn.com/w320/se.png",
            "svg": "https://flagcdn.com/se.svg"
        },
        "startOfWeek": "monday",
        "name": {
            "common": "Sweden",
            "official": "Kingdom of Sweden",
            "nativeName": {
                "swe": {
                    "official": "Konungariket Sverige",
                    "common": "Sverige"
                }
            }
        },
        "independent": true,
        "status": "officially-assigned",
        "unMember": true,
        "currencies": {
            "SEK": {
                "name": "Swedish krona",
                "symbol": "kr"
            }
        },
        "capital": [
            "Stockholm"
        ],
        "region": "Europe",
        "subregion": "Northern Europe",
        "languages": {
            "swe": "Swedish"
        },
        "population": 10353442
    },
    {
        "flags": {
            "png": "https://flagcdn.com/w320/nc.png",
            "svg": "https://flagcdn.com/nc.svg"
        },
        "startOfWeek": "monday",
        "name": {
            "common": "New Caledonia",
            "official": "New Caledonia",
            "nativeName": {
                "fra": {
                    "official": "Nouvelle-Calédonie",
                    "common": "Nouvelle-Calédonie"
                }
            }
        },
        "independent": false,
        "status": "officially-assigned",
        "unMember": false,
        "currencies": {
            "XPF": {
                "name": "CFP franc",
                "symbol": "₣"
            }
        },
        "capital": [
            "Nouméa"
        ],
        "region": "Oceania",
        "subregion": "Melanesia",
        "languages": {
            "fra": "French"
        },
        "population": 271960
    },
    {} ] """.trimIndent()
    )

fun testSuccessfulCountriesData() = listOf(
    CountryData(
        pngFlag = "https://flagcdn.com/w320/mt.png",
        svgFlag = null,
        startOfWeek = "monday",
        commonName = null,
        officialName = "Republic of Malta",
        nativeOfficialNames = mapOf(
            "mlt" to "Repubblika ta ' Malta",
        ),
        nativeCommonNames = mapOf(
            "eng" to "Malta",
            "mlt" to "Malta",
        ),
        independent = true,
        unMember = null,
        status = "officially-assigned",
        currencies = listOf(CurrencyData("EUR", "Euro", "€")),
        capital = emptyList(),
        region = "Europe",
        subregion = "Southern Europe",
        languages = mapOf(
            "eng" to "English",
            "mlt" to "Maltese",
        ),
        population = 525285,
    ),
    CountryData(
        pngFlag = "https://flagcdn.com/w320/se.png",
        svgFlag = "https://flagcdn.com/se.svg",
        startOfWeek = "monday",
        commonName = "Sweden",
        officialName = "Kingdom of Sweden",
        nativeOfficialNames = mapOf("swe" to "Konungariket Sverige"),
        nativeCommonNames = mapOf("swe" to "Sverige"),
        independent = true,
        unMember = true,
        status = "officially-assigned",
        currencies = listOf(CurrencyData("SEK", "Swedish krona", "kr")),
        capital = listOf("Stockholm"),
        region = "Europe",
        subregion = "Northern Europe",
        languages = mapOf("swe" to "Swedish"),
        population = 10353442,
    ),
    CountryData(
        pngFlag = "https://flagcdn.com/w320/nc.png",
        svgFlag = "https://flagcdn.com/nc.svg",
        startOfWeek = "monday",
        commonName = "New Caledonia",
        officialName = "New Caledonia",
        nativeOfficialNames = mapOf("fra" to "Nouvelle-Calédonie"),
        nativeCommonNames = mapOf("fra" to "Nouvelle-Calédonie"),
        independent = false,
        unMember = false,
        status = "officially-assigned",
        currencies = listOf(CurrencyData("XPF", "CFP franc", "₣")),
        capital = listOf("Nouméa"),
        region = "Oceania",
        subregion = "Melanesia",
        languages = mapOf("fra" to "French"),
        population = 271960,
    ),
    CountryData(
        pngFlag = null,
        svgFlag = null,
        startOfWeek = null,
        commonName = null,
        officialName = null,
        nativeOfficialNames = emptyMap(),
        nativeCommonNames = emptyMap(),
        independent = null,
        unMember = null,
        status = null,
        currencies = emptyList(),
        capital = emptyList(),
        region = null,
        subregion = null,
        languages = emptyMap(),
        population = null,
    )
)

fun testSuccessfulCountryListItems() = listOf(
    CountryListItem(
        CountryData(
            pngFlag = "https://flagcdn.com/w320/mt.png",
            svgFlag = null,
            startOfWeek = "monday",
            commonName = null,
            officialName = "Republic of Malta",
            nativeOfficialNames = mapOf(
                "mlt" to "Repubblika ta ' Malta",
            ),
            nativeCommonNames = mapOf(
                "eng" to "Malta",
                "mlt" to "Malta",
            ),
            independent = true,
            unMember = null,
            status = "officially-assigned",
            currencies = listOf(CurrencyData("EUR", "Euro", "€")),
            capital = emptyList(),
            region = "Europe",
            subregion = "Southern Europe",
            languages = mapOf(
                "eng" to "English",
                "mlt" to "Maltese",
            ),
            population = 525285,
        )
    ),
    CountryListItem(
        CountryData(
            pngFlag = "https://flagcdn.com/w320/se.png",
            svgFlag = "https://flagcdn.com/se.svg",
            startOfWeek = "monday",
            commonName = "Sweden",
            officialName = "Kingdom of Sweden",
            nativeOfficialNames = mapOf("swe" to "Konungariket Sverige"),
            nativeCommonNames = mapOf("swe" to "Sverige"),
            independent = true,
            unMember = true,
            status = "officially-assigned",
            currencies = listOf(CurrencyData("SEK", "Swedish krona", "kr")),
            capital = listOf("Stockholm"),
            region = "Europe",
            subregion = "Northern Europe",
            languages = mapOf("swe" to "Swedish"),
            population = 10353442,
        )
    ),
    CountryListItem(
        CountryData(
            pngFlag = "https://flagcdn.com/w320/nc.png",
            svgFlag = "https://flagcdn.com/nc.svg",
            startOfWeek = "monday",
            commonName = "New Caledonia",
            officialName = "New Caledonia",
            nativeOfficialNames = mapOf("fra" to "Nouvelle-Calédonie"),
            nativeCommonNames = mapOf("fra" to "Nouvelle-Calédonie"),
            independent = false,
            unMember = false,
            status = "officially-assigned",
            currencies = listOf(CurrencyData("XPF", "CFP franc", "₣")),
            capital = listOf("Nouméa"),
            region = "Oceania",
            subregion = "Melanesia",
            languages = mapOf("fra" to "French"),
            population = 271960,
        )
    ),
    CountryListItem(
        CountryData(
            pngFlag = null,
            svgFlag = null,
            startOfWeek = null,
            commonName = null,
            officialName = null,
            nativeOfficialNames = emptyMap(),
            nativeCommonNames = emptyMap(),
            independent = null,
            unMember = null,
            status = null,
            currencies = emptyList(),
            capital = emptyList(),
            region = null,
            subregion = null,
            languages = emptyMap(),
            population = null,
        )
    )
)

@Language("JSON")
fun testCountryNotFoundByName() = MockResponse()
    .setResponseCode(404)
    .setBody(
        """
            {
            	"status": 404,
            	"message": "Not Found"
            }
        """.trimIndent()
    )