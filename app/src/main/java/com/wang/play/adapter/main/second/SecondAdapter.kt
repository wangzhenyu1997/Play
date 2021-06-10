package com.wang.play.adapter.main.second

import android.app.Activity
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wang.mylibrary.util.MyApplicationLogUtil
import com.wang.play.MyApplication
import com.wang.play.R
import com.wang.play.data.main.second.UiModel

object DIFF : DiffUtil.ItemCallback<UiModel>() {
    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel) =
        (oldItem is UiModel.HitItem && newItem is UiModel.HitItem && (oldItem.hit.id == newItem.hit.id)) ||
                (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem && (oldItem.description == newItem.description))

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel) =
        oldItem == newItem
}

//定义Item点击事件
val hitItemOnClick: (View, UiModel.HitItem) -> Unit =
    { view, data ->
        val bundle = bundleOf("fragment_second_largeImageURL" to data.hit.largeImageURL)
        view.findNavController().navigate(
            R.id.action_navigation_second_to_imageDetailActivity,
            bundle, null
        )
    }

//定义Separator点击事件
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

class SecondAdapter :
    PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(DIFF) {


    class SecondViewHolder(
        itemView: View,
        private val onClick: (View, UiModel.HitItem) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView =
            itemView.findViewById(R.id.item_fragment_second_imageView)
        private val textView: TextView =
            itemView.findViewById(R.id.item_fragment_second_textView)

        private var itemInViewHolder: UiModel.HitItem? = null

        init {
            imageView.setOnClickListener { view ->
                itemInViewHolder?.let {
                    onClick(view, it)
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
            fun create(parent: ViewGroup): SecondViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_fragment_second, parent, false)
                return SecondViewHolder(view, hitItemOnClick)
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


    //这是分界线
    //___________________________________________________________________________________
    //___________________________________________________________________________________
    //___________________________________________________________________________________


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.HitItem -> R.layout.item_fragment_second
            is UiModel.SeparatorItem -> R.layout.item_fragment_second_separator
            else -> throw UnsupportedOperationException("Unknown view")
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_fragment_second) {
            SecondViewHolder.create(parent)
        } else {
            SeparatorViewHolder.create(parent)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val temp = getItem(position)

        temp?.let {
            when (it) {
                is UiModel.HitItem -> (holder as SecondViewHolder).bind(
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
