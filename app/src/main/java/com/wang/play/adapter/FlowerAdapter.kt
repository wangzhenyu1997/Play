package com.wang.play.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wang.play.R
import com.wang.play.datasource.room.entity.User


class FlowerAdapter : ListAdapter<User, FlowerAdapter.FlowerViewHolder>(FlowerDiffCallback) {

    object FlowerDiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    // 描述表项视图并且将它放在 RecyclerView 中
    class FlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flowerTextView: TextView = itemView.findViewById(R.id.item_flower_textView)

    }

    // 返回一个新的 ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flower, parent, false)

        return FlowerViewHolder(view)
    }


    // 显示一个指定位置的数据
    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {

        val text = "${getItem(position).firstName}  ${getItem(position).lastName}"

        holder.flowerTextView.text = text
    }


}