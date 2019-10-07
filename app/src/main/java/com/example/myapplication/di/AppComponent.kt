package com.example.myapplication.di

import com.example.myapplication.App
import com.example.myapplication.ui.post.PostDetailsFragment
import com.example.myapplication.ui.post.PostListFragment
import dagger.Component

@Component(
    modules = [
        DetailsModule::class
    ]
)

interface AppComponent {
    fun inject(fragment: App)
    fun inject(fragment: PostListFragment)
    fun inject(fragment: PostDetailsFragment)
}