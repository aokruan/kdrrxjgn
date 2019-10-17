package com.example.myapplication.service

import com.example.myapplication.domain.entity.AuthToken
import com.example.myapplication.domain.entity.Pagination
import com.example.myapplication.domain.entity.Post
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface Api {
    @GET("auth/products/{id}")
    fun getById(@Path("id") id: Long): Single<Post>

    @GET("auth/products")
    fun getAllPostsByPage(
        @Query("page") page: Int
    ): Single<Pagination<Post>>

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun signIn(
        @Body body: JsonObject
    ): Single<AuthToken>

    @Headers("Content-Type: application/json")
    @POST("auth/signup")
    fun signUp(
        @Body body: JsonObject
    ): Completable
}