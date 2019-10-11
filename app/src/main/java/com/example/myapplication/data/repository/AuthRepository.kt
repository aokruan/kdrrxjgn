package com.example.myapplication.data.repository

import com.example.myapplication.domain.entity.AuthToken
import com.example.myapplication.service.Api
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: Api) {
    fun signIn(email: String, password: String): Single<AuthToken> {
        val requestBody = JsonObject()
        requestBody.addProperty("email", email)
        requestBody.addProperty("password", password)
        return api.signIn(requestBody)
    }

    fun signUp(name:String, email: String, password: String, passwordConfirmation:String): Completable {
        val requestBody = JsonObject()
        requestBody.addProperty("name", name)
        requestBody.addProperty("email", email)
        requestBody.addProperty("password", password)
        requestBody.addProperty("password_confirmation", passwordConfirmation)
        return api.signUp(requestBody)
    }
}