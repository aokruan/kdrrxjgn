package com.example.myapplication.data.repository

import com.example.myapplication.domain.entity.Post
import com.example.myapplication.service.Api
import io.reactivex.Single
import javax.inject.Inject

class PostRepository @Inject constructor(private val api: Api) {
    fun getById(id: Long): Single<Post> = api.getById(id)

    fun getAll(): Single<List<Post>> = api.getAll()
}