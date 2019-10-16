package com.example.myapplication.domain.entity

import com.google.gson.annotations.SerializedName

class AuthToken(
    @SerializedName("access_token") val authToken: String
)