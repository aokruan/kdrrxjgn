package com.example.myapplication.di

import com.example.myapplication.App
import com.example.myapplication.data.repository.SettingsRepository
import com.example.myapplication.domain.interactor.PostInteractor
import com.example.myapplication.service.Api
import com.example.myapplication.service.ApiHolder
import com.example.myapplication.viewModel.order.OrderListViewModel
import com.example.myapplication.viewModel.post.PostDetailsViewModel
import com.example.myapplication.viewModel.post.PostListViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DetailsModule {
    @Singleton
    @Provides
    fun provideApi(apiHolder: ApiHolder): Api = apiHolder.api

    @Singleton
    @Provides
    fun provideApiHolder(gson: Gson): ApiHolder = ApiHolder(gson)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providePostListViewModel(postInteractor: PostInteractor): PostListViewModel =
        PostListViewModel(postInteractor)

    @Provides
    fun provideOrderListViewModel(postInteractor: PostInteractor): OrderListViewModel =
        OrderListViewModel(postInteractor)

    @Provides
    fun providePostDetailsViewModel(postInteractor: PostInteractor): PostDetailsViewModel =
        PostDetailsViewModel(postInteractor)

    @Provides
    fun provideSettingsRepository(gson: Gson): SettingsRepository =
        SettingsRepository(gson, App.sharedPreferences)
}