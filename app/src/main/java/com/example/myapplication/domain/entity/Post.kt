package com.example.myapplication.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: Long,
    val title: String,
    val description: String,
    val price: Int
): Parcelable