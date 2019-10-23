package com.example.myapplication.ui.auth

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.data.repository.SettingsRepository
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {
    private val disposeBag = CompositeDisposable()
    @Inject
    lateinit var settingsRepository: Lazy<SettingsRepository>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}
