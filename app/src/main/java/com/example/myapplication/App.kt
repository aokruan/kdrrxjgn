package com.example.myapplication

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.myapplication.data.repository.SettingsRepository
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.service.ApiHolder
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().context(this).create(this)
    }

    @Inject
    lateinit var settingsRepository: SettingsRepository
    @Inject
    lateinit var apiHolder: ApiHolder

    override fun onCreate() {
        super.onCreate()
        instance = this
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        settingsRepository.authToken?.accessToken?.let { token ->
            apiHolder.authToken = token
        }
    }

    companion object {
        lateinit var instance: App
        lateinit var sharedPreferences: SharedPreferences
    }
}