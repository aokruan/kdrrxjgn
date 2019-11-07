package com.example.myapplication.viewModel.settings

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SettingsViewModel @Inject constructor() : ViewModel() {
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.clear()
    }
}