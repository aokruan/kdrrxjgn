package com.example.myapplication.di

import com.example.myapplication.data.repository.PostRepository
import com.example.myapplication.domain.interactor.PostInteractor
import com.example.myapplication.service.ApiHolder
import com.example.myapplication.viewModel.post.PostDetailsViewModel
import com.example.myapplication.viewModel.post.PostListViewModel
import dagger.Module
import dagger.Provides

@Module
class DetailsModule {
    @Provides
    fun provideApi(): ApiHolder = ApiHolder()

    @Provides
    fun provideRepository(apiHolder: ApiHolder): PostRepository =
        PostRepository(apiHolder.api)

    @Provides
    fun provideInteractor(provideRepository: PostRepository): PostInteractor =
        PostInteractor(provideRepository)

    @Provides
    fun provideViewModel(postInteractor: PostInteractor): PostListViewModel =
        PostListViewModel(postInteractor)

    @Provides
    fun providePostDetailsViewModel(postInteractor: PostInteractor): PostDetailsViewModel =
        PostDetailsViewModel(postInteractor)

}