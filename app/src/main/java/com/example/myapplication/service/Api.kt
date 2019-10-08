package com.example.myapplication.service

import com.example.myapplication.domain.entity.Pagination
import com.example.myapplication.domain.entity.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("products")
    fun getAll(): Single<List<Post>>

    @GET("products/{id}")
    fun getById(@Path("id") id: Long): Single<Post>

    @GET("products")
    fun getAllPostsByPage(
        @Query("page") page: Int
    ): Single<Pagination<Post>>
}