package com.example.myapplication.data.repository

import com.example.myapplication.domain.entity.Post
import com.example.myapplication.domain.entity.Pagination
import com.example.myapplication.service.Api
import com.example.myapplication.service.ApiHolder
import io.reactivex.Single
import javax.inject.Inject

class PostRepository @Inject constructor(private val api: Api) {
    fun getById(id: Long): Single<Post> = api.getById(id)

    fun getAllPostsByPage(page: Int): Single<Pagination<Post>> =
        api.getAllPostsByPage(page)
}