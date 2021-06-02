package com.wang.play.adapter.test

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.MyApplication
import com.wang.play.R
import com.wang.play.data.test.UiModel

class TestAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(DIFF) {

    object DIFF : DiffUtil.ItemCallback<UiModel>() {
        override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel) =
            (oldItem is UiModel.HitItem && newItem is UiModel.HitItem && (oldItem.hit.id == newItem.hit.id)) ||
                    (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem && (oldItem.description == newItem.description))

        override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel) =
            oldItem == newItem
    }

    companion object {

        val hitItemOnClick: (UiModel.HitItem) -> Unit = {
            Toast.makeText(MyApplication.context, it.hit.largeImageURL, Toast.LENGTH_SHORT).show()
            MyApplicationLogUtil.d("TodayTest", it.hit.largeImageURL)
        }

        val separatorItemOnClick: (
            UiModel.HitItem?,
            UiModel.SeparatorItem
        ) -> Unit = { beforeItem, item ->
            Toast.makeText(
                MyApplication.context,
                "${beforeItem?.hit?.largeImageURL}    ${item.description}",
                Toast.LENGTH_SHORT
            ).show()
            MyApplicationLogUtil.d(
                "TodayTest",
                "${beforeItem?.hit?.largeImageURL}    ${item.description}"
            )
        }

    }

    class TestViewHolder(itemView: View, private val onClick: (UiModel.HitItem) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView =
            itemView.findViewById(R.id.item_fragment_second_imageView)
        private val textView: TextView =
            itemView.findViewById(R.id.item_fragment_second_textView)

        private var itemInViewHolder: UiModel.HitItem? = null

        init {
            imageView.setOnClickListener {
                itemInViewHolder?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: UiModel.HitItem) {

            itemInViewHolder = item

            imageView.load(item.hit.webformatURL) {
                crossfade(1000)
                placeholder(R.drawable.loading)
                error(R.drawable.error)
            }
            textView.text = item.hit.likes.toString()
        }

        companion object {
            inline fun create(parent: ViewGroup): TestViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_fragment_second, parent, false)
                return TestViewHolder(view, hitItemOnClick)
            }
        }
    }

    class SeparatorViewHolder(
        itemView: View,
        private val onclick: (UiModel.HitItem, UiModel.SeparatorItem) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val description: TextView =
            itemView.findViewById(R.id.item_fragment_second_separator_textView)
        private val button: ImageButton =
            itemView.findViewById(R.id.item_fragment_second_separator_button)

        private var beforeItemInViewHolder: UiModel.HitItem? = null
        private var itemInViewHolder: UiModel.SeparatorItem? = null


        //给Button绑定方法
        init {
            button.setOnClickListener {
                if (beforeItemInViewHolder != null && itemInViewHolder != null) {
                    onclick(beforeItemInViewHolder!!, itemInViewHolder!!)
                }
            }
        }

        fun bind(
            beforeItem: UiModel.HitItem?,
            item: UiModel.SeparatorItem,
        ) {

            beforeItemInViewHolder = beforeItem
            itemInViewHolder = item

            if (item.isTitle) {
                button.visibility = View.GONE
                description.text = item.description
            } else {
                description.text = "${item.description} ❤"
                description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
        }

        companion object {
            fun create(parent: ViewGroup): SeparatorViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_fragment_second_separator, parent, false)
                return SeparatorViewHolder(view, separatorItemOnClick)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {

        return when (getItem(position)) {
            is UiModel.HitItem -> R.layout.item_fragment_second
            is UiModel.SeparatorItem -> R.layout.item_fragment_second_separator
            else -> throw UnsupportedOperationException("Unknown view")
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_fragment_second) {
            TestViewHolder.create(parent)
        } else {
            SeparatorViewHolder.create(parent)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val temp = getItem(position)

        temp?.let {
            when (it) {
                is UiModel.HitItem -> (holder as TestViewHolder).bind(
                    it
                )
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(
                    if ((position - 1) < 0) null else getItem(position - 1) as UiModel.HitItem,
                    it
                )

            }
        }

    }
}
