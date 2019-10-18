package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.data.repository.SettingsRepository
import dagger.Lazy
import dagger.android.AndroidInjection
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

        AndroidInjection.inject(this)

        settingsRepository.get().isValidated?.let { routeToFragment(it) }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_recents -> ""
                R.id.action_favorites -> ""
                R.id.action_nearby -> ""
            }
            true
        }
    }

    private fun routeToFragment(fragment: Int) {
        findNavController(R.id.main_host_fragment).navigate(fragment, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}
