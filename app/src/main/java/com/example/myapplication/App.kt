package com.example.myapplication

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.myapplication.di.DaggerAppComponent
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().context(this).create(this)
    }

    val gson: Gson = GsonBuilder()
        .create()

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    }

    companion object {
        lateinit var instance: App
        lateinit var sharedPreferences: SharedPreferences
    }
}