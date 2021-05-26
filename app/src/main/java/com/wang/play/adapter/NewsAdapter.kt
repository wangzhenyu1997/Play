package com.wang.play.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.wang.play.R
import com.wang.play.data.main.second.News

class NewsAdapter : PagingDataAdapter<News.Data, NewsAdapter.NewsViewModel>(NewsDiffCallback) {

    object NewsDiffCallback : DiffUtil.ItemCallback<News.Data>() {
        override fun areItemsTheSame(oldItem: News.Data, newItem: News.Data) =
            oldItem.uniquekey == newItem.uniquekey

        override fun areContentsTheSame(oldItem: News.Data, newItem: News.Data) =
            oldItem == newItem

    }

    class NewsViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.item_news_title)

        val authorName: TextView = itemView.findViewById(R.id.item_news_author_name)
        val date: TextView = itemView.findViewById(R.id.item_news_date)

        val viewpager2: ViewPager2 = itemView.findViewById(R.id.item_news_viewPager2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewModel {
        val holder = NewsViewModel(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
//        holder.title.setOnClickListener {
//            Toast.makeText(
//                MyApplication.context,
//                " ${getItem(holder.absoluteAdapterPosition)?.thumbnail_pic_s}  ${getItem(holder.absoluteAdapterPosition)?.thumbnail_pic_s02}  ${
//                    getItem(
//                        holder.absoluteAdapterPosition
//                    )?.thumbnail_pic_s03
//                } ",
//                Toast.LENGTH_SHORT
//            ).show()
//
//        }双击跳转
        return holder
    }


    override fun onBindViewHolder(holder: NewsViewModel, position: Int) {

        val temp = getItem(position)

        holder.title.text = temp?.title
        holder.authorName.text = temp?.author_name
        holder.date.text = temp?.date

        val list = mutableListOf<String>()
        temp?.thumbnail_pic_s?.apply {
            list.add(this)
        }
        temp?.thumbnail_pic_s02?.apply {
            list.add(this)
        }
        temp?.thumbnail_pic_s03?.apply {
            list.add(this)
        }

        val imageViewAdapter = ImageViewAdapter()

        holder.viewpager2.adapter = imageViewAdapter
        imageViewAdapter.submitList(list)
    }


}


//
//    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
//        val aaurl: String = getItem(position).url
//        //  holder.testImageView.load(url)
//
//        //Glide.with(MyApplication.context).load(R.drawable.cat).into(holder.testImageView);
//
//        holder.testImageView.load(aaurl) {
//            crossfade(true)
//            placeholder(R.drawable.cat)
//            //error(R.drawable.cat)
//        }
//