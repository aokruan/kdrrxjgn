package com.example.myapplication.ui.order

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.presentation.inflate
import kotlinx.android.synthetic.main.order_item.view.*
import kotlin.properties.Delegates

class OrderListAdapter(
    /*private val onPostClick: (post: Post) -> Unit*/
) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    var postList: List<Post> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        parent.inflate(R.layout.order_item).let(::ViewHolder)

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(post: Post) {
            itemView.setOnClickListener {
                /*onPostClick(post)*/
            }
            with(itemView) {
                tvTitle.text = post.title
                tvDescription.text = post.description
                tvPrice.text = post.price.toString()
                Glide.with(context)
                    .load(post.image)
                    .override(128, 128)
                    .fitCenter()
                    .into(ivImage)

                btnRemoveProduct?.setOnClickListener {
                    val tvAmount = tvAmountOfItemsAdded.text
                    when (tvAmount.toString()) {
                        "1" -> {
                            tvAmountOfItemsAdded.text = 1.toString()
                            clAddRemoveProduct.visibility = View.INVISIBLE
                        }
                        else -> {
                            tvAmountOfItemsAdded.text =
                                (tvAmountOfItemsAdded.text.toString().toInt() - 1).toString()
                        }
                    }
                }

                btnAddProduct?.setOnClickListener {
                    val tvAmount = tvAmountOfItemsAdded.text
                    if (tvAmount.toString() != 10.toString()) {
                        tvAmountOfItemsAdded.text =
                            (tvAmountOfItemsAdded.text.toString().toInt() + 1).toString()
                        clAddRemoveProduct.visibility = View.VISIBLE
                    } else {
                        tvAmountOfItemsAdded.text = 10.toString()
                    }
                }
            }
        }
    }
}