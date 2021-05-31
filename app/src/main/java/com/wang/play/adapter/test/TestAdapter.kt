package com.wang.play.adapter.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wang.play.R
import com.wang.play.data.test.Hit

class TestAdapter : PagingDataAdapter<Hit, TestAdapter.TestViewHolder>(DIFF) {

    object DIFF : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit) =
            oldItem == newItem

    }


    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val holder = TestViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_view, parent, false)
        )


        return holder
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val temp = getItem(position)

        holder.imageView.load(temp?.webformatURL)
        {
            crossfade(1000)
            placeholder(R.drawable.loading)
            error(R.drawable.error)
        }


    }

}

