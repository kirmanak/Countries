package com.majority.countries.data

import com.majority.countries.data.models.CountryModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestCountriesApi {
    @GET("/v3.1/all")
    suspend fun getAll(
        @Query("fields") fields: String,
    ): List<CountryModel>

    @GET("/v3.1/name/{name}")
    suspend fun getByName(
        @Path("name") name: String,
        @Query("fields") fields: String,
    ): List<CountryModel>
}