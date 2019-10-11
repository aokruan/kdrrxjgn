package com.example.myapplication.service

import com.example.myapplication.App
import com.example.myapplication.data.service.AuthInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiHolder {
    private val BASE_URL: String = "https://api.myjson.com/bins/"
    private val LARAVEL_API_URL: String = "http://10.214.1.55/api/"

    private val gson = App.instance.gson
    private val authInterceptor = AuthInterceptor()
    val api: Api = initApi()

    private fun initApi(): Api {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(authInterceptor)

        return Retrofit.Builder()
            .client(httpClientBuilder.build())
            .baseUrl(LARAVEL_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(Api::class.java)
    }
}