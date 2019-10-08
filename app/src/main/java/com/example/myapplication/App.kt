package com.example.myapplication

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApp
    }


    val gson: Gson = GsonBuilder()
        .create()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}