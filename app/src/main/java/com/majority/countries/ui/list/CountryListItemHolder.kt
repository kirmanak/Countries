package com.majority.countries.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.majority.countries.databinding.VhCountryBinding
import com.majority.countries.ui.ImageLoader
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.onClosed
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import timber.log.Timber
import javax.inject.Inject

class CountryListItemHolder(
    private val imageLoader: ImageLoader,
    private val binding: VhCountryBinding,
    private val clicksChannel: SendChannel<CountryListItem>,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CountryListItem) = with(binding) {
        capital.text = item.capital
        name.text = item.name
        imageLoader.loadImage(flag, item.pngFlag)
        root.setOnClickListener {
            Timber.d("bind: on item clicked $item")
            clicksChannel.trySend(item)
                .onClosed { Timber.w("bind: can't send click, closed") }
                .onFailure { Timber.e(it, "bind: can't send click, failed") }
                .onSuccess { Timber.d("bind: managed to send click") }
        }
    }

    class Factory @Inject constructor(private val imageLoader: ImageLoader) {
        fun createHolder(binding: VhCountryBinding, clicksChannel: SendChannel<CountryListItem>) =
            CountryListItemHolder(imageLoader, binding, clicksChannel)
    }
}
