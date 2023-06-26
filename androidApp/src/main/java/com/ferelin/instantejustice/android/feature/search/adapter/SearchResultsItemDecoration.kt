package com.ferelin.instantejustice.android.feature.search.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ferelin.instantejustice.android.R

class SearchResultsItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = view.context.resources.getDimension(R.dimen.search_result_horizontal).toInt()
        outRect.right =
            view.context.resources.getDimension(R.dimen.search_result_horizontal).toInt()
        outRect.top = view.context.resources.getDimension(R.dimen.search_result_top).toInt()
    }
}