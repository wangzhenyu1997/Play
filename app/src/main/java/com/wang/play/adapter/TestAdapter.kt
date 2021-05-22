package com.wang.play.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.R
import com.wang.play.data.main.Beautiful

class TestAdapter : ListAdapter<Beautiful.Data, TestAdapter.TestViewHolder>(TestDiffCallback) {

    object TestDiffCallback : DiffUtil.ItemCallback<Beautiful.Data>() {
        override fun areItemsTheSame(oldItem: Beautiful.Data, newItem: Beautiful.Data) =
            oldItem._id == newItem._id

        override fun areContentsTheSame(oldItem: Beautiful.Data, newItem: Beautiful.Data) =
            oldItem == newItem

    }

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val testImageView: ImageView = itemView.findViewById(R.id.item_beautiful_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_beautiful, parent, false)

        return TestViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val aaurl: String = getItem(position).url
        //  holder.testImageView.load(url)

        //Glide.with(MyApplication.context).load(R.drawable.cat).into(holder.testImageView);

        holder.testImageView.load(aaurl) {
            crossfade(true)
            placeholder(R.drawable.cat)
            //error(R.drawable.cat)
        }
        //https://ae01.alicdn.com/kf/Uec00959acd9c4d0aa900d5fb8ea481931.jpg
        //http://gank.io/images/f4f6d68bf30147e1bdd4ddbc6ad7c2a2



        MyApplicationLogUtil.d("AAAAAAAAAAAA", aaurl)
    }

}