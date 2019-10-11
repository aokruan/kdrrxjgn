package com.example.myapplication.domain.interactor

import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.domain.entity.AuthToken
import io.reactivex.Single
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val repository: AuthRepository) {
    fun signIn(email: String, password: String): Single<AuthToken> =
        repository.signIn(email, password)

    fun signUp(name:String, email: String, password: String, passwordConfirmation:String) =
        repository.signUp(name, email, password, passwordConfirmation)
}