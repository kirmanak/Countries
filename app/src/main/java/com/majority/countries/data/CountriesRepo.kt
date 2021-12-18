package com.majority.countries.data

interface CountriesRepo {
    suspend fun getAllCountries(): List<CountryData>

    suspend fun searchByCountryName(name: String): List<CountryData>
}