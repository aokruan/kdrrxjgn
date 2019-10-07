package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.AppComponent
import com.example.myapplication.di.DaggerAppComponent
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .build()
    }

    val gson: Gson = GsonBuilder()
        .create()

    override fun onCreate() {
        super.onCreate()
        instance = this

        component.inject(this)
    }

    companion object {
        lateinit var instance: App
    }
}