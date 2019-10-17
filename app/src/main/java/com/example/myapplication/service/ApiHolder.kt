package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.service.AuthInterceptor
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiHolder(var gson: Gson) {
    private val url = "http://10.214.1.55/api/"

    private val authInterceptor = AuthInterceptor()
    var api: Api = initApi()

    var authToken: String? = null
        set(value) {
            field = value
            authInterceptor.token = value
        }

    private fun initApi(): Api {
        val httpClientBuilder = OkHttpClient.Builder()
        val logging = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(Level.HEADERS)
        } else {
            HttpLoggingInterceptor().setLevel(Level.BASIC)
        }

        httpClientBuilder.addInterceptor(authInterceptor)
        httpClientBuilder.addInterceptor(logging)

        return Retrofit.Builder()
            .client(httpClientBuilder.build())
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(Api::class.java)
    }
}