package com.ferelin.instantejustice.android.feature.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ferelin.instantejustice.android.databinding.ItemErrorBinding
import com.ferelin.instantejustice.android.databinding.ItemLoadingBinding

private const val VIEW_TYPE_PROGRESS = 0
private const val VIEW_TYPE_ERROR = 1

class SearchResultsLoadAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<SearchResultsLoadAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when (loadState) {
            is LoadState.Loading -> {
                ProgressViewHolder(
                    ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            is LoadState.Error -> {
                ErrorViewHolder(
                    ItemErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    retry
                )
            }

            else -> error("Not supported load state: $loadState")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return when (loadState) {
            is LoadState.Loading -> VIEW_TYPE_PROGRESS
            is LoadState.Error -> VIEW_TYPE_ERROR
            else -> error("Not supported load state: $loadState")
        }
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(loadState: LoadState)
    }

    class ProgressViewHolder(binding: ItemLoadingBinding) : ItemViewHolder(binding.root) {
        override fun bind(loadState: LoadState) = Unit
    }

    class ErrorViewHolder(
        private val binding: ItemErrorBinding,
        private val retry: () -> Unit
    ) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            binding.textViewErrorMessage.text = (loadState as LoadState.Error).error.toString()
            binding.buttonRetry.setOnClickListener { retry.invoke() }
        }
    }
}