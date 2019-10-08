package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(
    modules = [
        AndroidInjectionModule::class,
        DetailsModule::class,
        AppModule::class
    ]
)

interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {
        @BindsInstance
        abstract fun context(context: Context): Builder
    }
}