package com.example.myapplication.domain.interactor

import android.util.Log
import com.example.myapplication.data.repository.PostRepository
import com.example.myapplication.data.repository.SettingsRepository
import com.example.myapplication.domain.entity.Pagination
import com.example.myapplication.domain.entity.Post
import io.reactivex.Single
import javax.inject.Inject

class PostInteractor @Inject constructor(private val postRepository: PostRepository,
                                         private val settingsRepository: SettingsRepository ) {
    fun getById(id: Long): Single<Post> =
        postRepository.getById(id)

    fun getAllPostsByPage(page: Int): Single<Pagination<Post>> =
        postRepository.getAllPostsByPage(page).doOnSubscribe{
            Log.e("settRepo_authToken",settingsRepository.authToken?.authToken)
        }

    fun getAll(): Single<List<Post>> = postRepository.getAll()
}