package com.majority.countries.di

import com.majority.countries.data.CountriesRepo
import com.majority.countries.data.impl.CountriesRepoRetrofitImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindCountriesRepo(countriesRepoRetrofitImpl: CountriesRepoRetrofitImpl): CountriesRepo
}