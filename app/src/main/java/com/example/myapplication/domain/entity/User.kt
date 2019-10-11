package com.example.myapplication.domain.entity

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("access_token") val accessToken: String
)