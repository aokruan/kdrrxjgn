package com.example.myapplication.data.repository

import android.content.SharedPreferences
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.domain.entity.AuthToken
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {
    var authToken: AuthToken?
        get() {
            return try {
                gson.fromJson(sharedPreferences.getString("authToken", ""), AuthToken::class.java)
            } catch (e: JsonSyntaxException) {
                null
            }
        }
        set(value) {
            sharedPreferences.edit()
                .putString("authToken", gson.toJson(value))
                .apply()
        }

    val isValidated: Int?
        get() {
            val accessToken = App.sharedPreferences.getString("authToken", "").toString()
            return if (accessToken.isNotBlank() && accessToken.length == 1091)
                R.id.actionToPostList else R.id.authFragment
        }
}