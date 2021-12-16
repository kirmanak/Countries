package com.majority.countries.ui.list

import androidx.recyclerview.widget.DiffUtil
import timber.log.Timber

object CountryListItemDiffCallback : DiffUtil.ItemCallback<CountryListItem>() {
    override fun areItemsTheSame(oldItem: CountryListItem, newItem: CountryListItem): Boolean {
        Timber.v("areItemsTheSame() called with: oldItem = $oldItem, newItem = $newItem")
        val result = oldItem.name == newItem.name
        Timber.v("areItemsTheSame() returned: $result")
        return result
    }

    override fun areContentsTheSame(oldItem: CountryListItem, newItem: CountryListItem): Boolean {
        Timber.v("areContentsTheSame() called with: oldItem = $oldItem, newItem = $newItem")
        val result = oldItem == newItem
        Timber.v("areContentsTheSame() returned: $result")
        return result
    }
}
