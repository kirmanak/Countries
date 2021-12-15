package com.majority.countries

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majority.countries.databinding.AcCountriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesActivity : AppCompatActivity() {

    private val binding: AcCountriesBinding by viewBinding(AcCountriesBinding::bind, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_countries)
    }
}