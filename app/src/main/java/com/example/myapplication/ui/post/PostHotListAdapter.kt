package com.example.myapplication.ui.post

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.domain.entity.Post
import com.example.myapplication.presentation.inflate
import kotlinx.android.synthetic.main.post_item.view.*
import kotlin.properties.Delegates

class PostHotListAdapter(
    /*private val onPostClick: (post: Post) -> Unit*/
) : RecyclerView.Adapter<PostHotListAdapter.ViewHolder>() {
    var postList: List<Post> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        parent.inflate(R.layout.hot_item).let(::ViewHolder)

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
                tvPrice.text = post.price.toString()
                Glide.with(context)
                    .load(post.image)
                    .override(128, 128)
                    .fitCenter()
                    .into(ivImage)
            }
        }
    }
}