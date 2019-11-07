package com.example.myapplication.ui.settings

import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.example.myapplication.R
import com.example.myapplication.ui.base.BaseFragment
import com.example.myapplication.viewModel.settings.SettingsViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }


    @Inject
    lateinit var viewModel: SettingsViewModel


}