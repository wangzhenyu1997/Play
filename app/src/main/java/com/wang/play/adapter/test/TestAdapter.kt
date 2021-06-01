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

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView =
            itemView.findViewById(R.id.item_fragment_second_imageView)
        private val textView: TextView =
            itemView.findViewById(R.id.item_fragment_second_textView)

        init {
            imageView.setOnClickListener {
                Toast.makeText(MyApplication.context, "查看大图", Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(url: String, like: String) {
            imageView.load(url) {
                crossfade(1000)
                placeholder(R.drawable.loading)
                error(R.drawable.error)
            }
            textView.text = like
        }

        companion object {
            fun create(parent: ViewGroup): TestViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_fragment_second, parent, false)
                return TestViewHolder(view)
            }
        }
    }

    class SeparatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val description: TextView =
            itemView.findViewById(R.id.item_fragment_second_separator_textView)
        private val button: ImageButton =
            itemView.findViewById(R.id.item_fragment_second_separator_button)

        //给Button绑定方法
        init {
            button.setOnClickListener {
                Toast.makeText(MyApplication.context, "AAA", Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(descriptionText: String, isTitle: Boolean) {
            if (isTitle) {
                button.visibility = View.GONE
                description.text = descriptionText
            } else {
                description.text = "$descriptionText ❤"
                description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
            }
        }

        companion object {
            fun create(parent: ViewGroup): SeparatorViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_fragment_second_separator, parent, false)
                return SeparatorViewHolder(view)
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
                    it.hit.webformatURL,
                    it.hit.likes.toString()
                )
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).bind(
                    it.description,
                    it.isTitle
                )

            }
        }


    }


}
