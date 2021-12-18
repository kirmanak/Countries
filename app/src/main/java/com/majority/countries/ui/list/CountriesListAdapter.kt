package com.majority.countries.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.majority.countries.databinding.VhCountryBinding
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesListAdapter @Inject constructor(
    private val viewHolderFactory: CountryListItemHolder.Factory,
) : ListAdapter<CountryListItem, CountryListItemHolder>(CountryListItemDiffCallback) {

    private val clicksChannel: Channel<CountryListItem> = Channel()
    val clicksPublisher: ReceiveChannel<CountryListItem> by ::clicksChannel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListItemHolder {
        Timber.v("onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VhCountryBinding.inflate(layoutInflater, parent, false)
        return viewHolderFactory.createHolder(binding, clicksChannel)
    }

    override fun onBindViewHolder(holder: CountryListItemHolder, position: Int) {
        Timber.v("onBindViewHolder() called with: holder = $holder, position = $position")
        getItem(position)?.let { holder.bind(it) }
    }
}