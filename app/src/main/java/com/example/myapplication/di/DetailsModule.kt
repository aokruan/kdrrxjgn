package com.example.myapplication.di

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.repository.SettingsRepository
import com.example.myapplication.domain.interactor.PostInteractor
import com.example.myapplication.service.Api
import com.example.myapplication.service.ApiHolder
import com.example.myapplication.viewModel.post.PostDetailsViewModel
import com.example.myapplication.viewModel.post.PostListViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
class DetailsModule {
    @Provides
    fun provideApi(gson: Gson): Api = ApiHolder(gson).api

    @Provides
    fun provideViewModel(postInteractor: PostInteractor): PostListViewModel =
        PostListViewModel(postInteractor)

    @Provides
    fun providePostDetailsViewModel(postInteractor: PostInteractor): PostDetailsViewModel =
        PostDetailsViewModel(postInteractor)

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)

    @Provides
    fun provideSettingsRepository(gson: Gson, source: SharedPreferences): SettingsRepository =
        SettingsRepository(source, gson)
}