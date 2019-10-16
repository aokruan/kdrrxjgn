package com.example.myapplication.data.service

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(var token: String? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e("AUTH INTERCEPTOR", token ?: "Token is null")
        val request = token?.let {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } ?: chain.request()
        return chain.proceed(request)
    }
}