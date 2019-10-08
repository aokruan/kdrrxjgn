package com.example.myapplication.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*@Parcelize
data class Post(
    val id: Long,
    val title: String,
    val description: String,
    val price: Int
): Parcelable*/


data class Pagination<T>(
    val current_page: Int,
    val data: List<T>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    val next_page_url: String,
    val path: String,
    val per_page: Int,
    val prev_page_url: Any,
    val to: Int,
    val total: Int
)

@Parcelize
data class Post(
    val availability: Int,
    val created_at: String,
    val description: String,
    val id: Long,
    val price: Int,
    val title: String,
    val updated_at: String
) : Parcelable