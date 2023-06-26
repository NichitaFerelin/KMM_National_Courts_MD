package com.ferelin.instantejustice.android.feature.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ferelin.instantejustice.domain.InstanteJusticeItem

object InstanteJusticeItemComparator : DiffUtil.ItemCallback<InstanteJusticeItem>() {

    override fun areItemsTheSame(
        oldItem: InstanteJusticeItem,
        newItem: InstanteJusticeItem
    ): Boolean {
        return false
    }

    override fun areContentsTheSame(
        oldItem: InstanteJusticeItem,
        newItem: InstanteJusticeItem
    ): Boolean {
        return oldItem == newItem
    }
}