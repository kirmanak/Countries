package com.majority.countries.data

interface CountriesRepo {
    suspend fun getAllCountries(): List<CountryData>
}