package com.wang.play.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wang.play.R

class ImageViewAdapter : ListAdapter<String, ImageViewAdapter.VH>(diff) {

    companion object {

        private val diff = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

        }

    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_view, parent, false)
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.imageView.load(getItem(position))
        {
            crossfade(true)
            error(R.drawable.error)
        }
    }


}
