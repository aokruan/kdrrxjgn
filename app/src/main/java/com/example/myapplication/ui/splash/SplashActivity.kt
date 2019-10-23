package com.example.myapplication.ui.splash

import android.content.Intent
import android.os.Bundle
import com.example.myapplication.data.repository.SettingsRepository
import com.example.myapplication.ui.auth.AuthActivity
import com.example.myapplication.ui.main.MainActivity
import dagger.android.DaggerActivity
import javax.inject.Inject

class SplashActivity : DaggerActivity() {
    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = settingsRepository.authToken?.let {
            if (it.authToken.isEmpty()) {
                Intent(this, AuthActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }
        } ?: Intent(this, AuthActivity::class.java)

        startActivity(intent)
        finish()
    }
}
