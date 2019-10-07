package com.example.myapplication.service

import com.example.myapplication.domain.entity.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("rykxj")
    fun getAll(): Single<List<Post>>

    @GET("rykxj/{id}")
    fun getById(@Path("id") id: Long): Single<Post>
}