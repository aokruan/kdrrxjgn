package com.example.myapplication.di

import com.example.myapplication.App
import com.example.myapplication.data.repository.SettingsRepository
import com.example.myapplication.domain.interactor.PostInteractor
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.auth.AuthFragment
import com.example.myapplication.ui.auth.SignUpFragment
import com.example.myapplication.ui.main.MainActivity
import com.example.myapplication.ui.post.PostDetailsFragment
import com.example.myapplication.ui.post.PostListAdapter
import com.example.myapplication.ui.post.PostListFragment
import com.example.myapplication.ui.settings.SettingsFragment
import com.example.myapplication.ui.splash.SplashActivity
import com.example.myapplication.viewModel.post.PostListViewModel
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface AppModule {
    @ContributesAndroidInjector
    fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    fun authActivityInjector(): AuthActivity

    @ContributesAndroidInjector
    fun splashActivityInjector(): SplashActivity

    @ContributesAndroidInjector
    fun postListFragmentInjector(): PostListFragment

    @ContributesAndroidInjector
    fun postDetailsFragmentInjector(): PostDetailsFragment

    @ContributesAndroidInjector
    fun authFragmentInjector(): AuthFragment

    @ContributesAndroidInjector
    fun signUpFragmentInjector(): SignUpFragment

    @ContributesAndroidInjector
    fun settingsFragmentInjector(): SettingsFragment

/*    @ContributesAndroidInjector
    fun settingsRepositoryInjector(): SettingsRepository*/

    @ContributesAndroidInjector
    fun appInjector(): App

    @ContributesAndroidInjector
    fun gsonInject(): Gson
}