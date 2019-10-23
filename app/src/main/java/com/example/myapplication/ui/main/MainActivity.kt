package com.example.myapplication.ui.main

import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.data.repository.SettingsRepository
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {
    private val disposeBag = CompositeDisposable()

    @Inject
    lateinit var settingsRepository: Lazy<SettingsRepository>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Conceptive"
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_recents -> ""
                R.id.action_favorites -> ""
                R.id.action_nearby -> ""
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}
