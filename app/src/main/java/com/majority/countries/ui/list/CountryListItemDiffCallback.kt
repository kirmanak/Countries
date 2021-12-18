package com.majority.countries.ui.list

import androidx.recyclerview.widget.DiffUtil

object CountryListItemDiffCallback : DiffUtil.ItemCallback<CountryListItem>() {
    override fun areItemsTheSame(oldItem: CountryListItem, newItem: CountryListItem) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CountryListItem, newItem: CountryListItem) =
        oldItem == newItem
}
