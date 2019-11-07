package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
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

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnRecents -> findNavController(main_host_fragment).navigate(R.id.actionToPostListFragment)
                R.id.action_favorites -> ""
                R.id.bnNearby -> findNavController(main_host_fragment).navigate(R.id.actionToSettings)
            }
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}
