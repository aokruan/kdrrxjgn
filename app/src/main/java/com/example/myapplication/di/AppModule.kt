package com.example.myapplication.di

import com.example.myapplication.ui.auth.AuthFragment
import com.example.myapplication.ui.auth.SignUpFragment
import com.example.myapplication.ui.main.MainActivity
import com.example.myapplication.ui.post.PostDetailsFragment
import com.example.myapplication.ui.post.PostListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface AppModule {
    @ContributesAndroidInjector
    fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    fun postListFragmentInjector(): PostListFragment

    @ContributesAndroidInjector
    fun postDetailsFragmentInjector(): PostDetailsFragment

    @ContributesAndroidInjector
    fun authFragmentInjector(): AuthFragment

    @ContributesAndroidInjector
    fun signUpFragmentInjector(): SignUpFragment
}