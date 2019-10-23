package com.example.myapplication.data.repository

import com.example.myapplication.domain.entity.AuthToken
import com.example.myapplication.service.Api
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: Api) {
    fun signIn(email: String, password: String): Single<AuthToken> {
        return api.signIn(JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
        })
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ): Completable {
        return api.signUp(JsonObject().apply {
            addProperty("name", name)
            addProperty("email", email)
            addProperty("password", password)
            addProperty("password_confirmation", passwordConfirmation)
        }
        )
    }
}