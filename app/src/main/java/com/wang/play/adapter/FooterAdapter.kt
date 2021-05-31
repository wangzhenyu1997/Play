package com.wang.play.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wang.play.R
import com.wang.play.databinding.ItemFooterBinding


class FooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterAdapter.HitLoadStateViewHolder>() {


    class HitLoadStateViewHolder(
        private val binding: ItemFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemFooterButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {

            binding.itemFooterProgressBar.isVisible = loadState is LoadState.Loading
            if (loadState is LoadState.Error) {
                binding.itemFooterTextView.text = loadState.error.localizedMessage
            }
            binding.itemFooterButton.isVisible = loadState is LoadState.Error
            binding.itemFooterTextView.isVisible = loadState is LoadState.Error

        }


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): HitLoadStateViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_footer, parent, false)

        //绑定视图
        val binding = ItemFooterBinding.bind(view)

        return HitLoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: HitLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}