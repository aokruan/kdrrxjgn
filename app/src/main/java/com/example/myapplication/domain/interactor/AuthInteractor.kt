package com.example.myapplication.domain.interactor

import android.util.Log
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.data.repository.SettingsRepository
import com.example.myapplication.domain.entity.AuthToken
import com.example.myapplication.service.ApiHolder
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val repository: AuthRepository,
    private val settingsRepository: SettingsRepository,
    private val apiHolder: ApiHolder
) {
    fun signIn(email: String, password: String): Single<AuthToken> =
        repository.signIn(email, password)
            .doOnSuccess(::onSuccessAuth)

    private fun onSuccessAuth(authToken: AuthToken) {
        Log.e("authToken", authToken.authToken)
        settingsRepository.authToken = authToken
        apiHolder.authToken = authToken.authToken
    }

    fun logOut(): Completable = Completable.fromAction {
        settingsRepository.authToken = null
        apiHolder.authToken = null
    }

    fun signUp(name: String, email: String, password: String, passwordConfirmation: String) =
        repository.signUp(name, email, password, passwordConfirmation)
}