package com.example.myapplication.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.myapplication.R

enum class EmptyList(
    @StringRes val message: Int,
    @DrawableRes val icon: Int
) {
    NONE(0, 0),
    LIST(R.string.post_empty_list, R.drawable.ic_empty_list)
}