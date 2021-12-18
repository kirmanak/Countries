package com.majority.countries.data.impl

import com.majority.countries.data.CountriesRepo
import com.majority.countries.data.CountryData
import com.majority.countries.data.CurrencyData
import com.majority.countries.data.RestCountriesApi
import com.majority.countries.data.models.CountryModel
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class CountriesRepoRetrofitImpl @Inject constructor(
    private val api: RestCountriesApi,
) : CountriesRepo {

    override suspend fun getAllCountries(): List<CountryData> {
        Timber.v("getAllCountries() called")
        return mapCountryInfo(api.getAll(FIELDS))
    }

    override suspend fun searchByCountryName(name: String): List<CountryData> {
        Timber.v("searchByCountryName() called with: name = $name")
        val response = api.getByName(name, FIELDS)
        return when {
            response.isSuccessful -> {
                Timber.d("searchByCountryName: response is successful")
                val body = checkNotNull(response.body()) { "Response successful but body is null" }
                mapCountryInfo(body)
            }
            response.code() == 404 -> {
                Timber.w("searchByCountryName: 404 is received, returning empty list")
                emptyList()
            }
            else -> {
                Timber.w("searchByCountryName: received unsuccessful response but not 404")
                throw HttpException(response)
            }
        }
    }

    private fun mapCountryInfo(response: List<CountryModel>): List<CountryData> {
        Timber.v("mapCountryInfo() called with: response = $response")
        return response.map { model ->
            val nativeOfficialNames = mutableMapOf<String, String>()
            val nativeCommonNames = mutableMapOf<String, String>()
            model.populateNameMaps(nativeCommonNames, nativeOfficialNames)

            CountryData(
                pngFlag = model.flags.png,
                svgFlag = model.flags.svg,
                startOfWeek = model.startOfWeek,
                commonName = model.name.common,
                officialName = model.name.official,
                nativeOfficialNames = nativeOfficialNames,
                nativeCommonNames = nativeCommonNames,
                independent = model.independent,
                unMember = model.unMember,
                status = model.status,
                currencies = model.parseCurrencies(),
                capitals = model.capital,
                region = model.region,
                subregion = model.subregion,
                languages = model.languages,
                population = model.population,
            )
        }
    }

    private fun CountryModel.populateNameMaps(
        nativeCommonNames: MutableMap<String, String>,
        nativeOfficialNames: MutableMap<String, String>
    ) {
        name.nativeName.forEach { entry ->
            entry.value.common?.let { nativeCommonNames[entry.key] = it }
            entry.value.official?.let { nativeOfficialNames[entry.key] = it }
        }
    }

    private fun CountryModel.parseCurrencies(): List<CurrencyData> =
        currencies.mapNotNull {
            val symbol = it.value.symbol ?: return@mapNotNull null
            val name = it.value.name ?: return@mapNotNull null
            CurrencyData(it.key, name, symbol)
        }

    companion object {
        private val FIELDS = arrayOf(
            "name",
            "independent",
            "status",
            "unMember",
            "currencies",
            "capital",
            "population",
            "flags",
            "region",
            "subregion",
            "languages",
            "startOfWeek"
        ).joinToString(",")
    }
}