package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable

class MainActivity : DaggerAppCompatActivity() {

    private val disposeBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}
