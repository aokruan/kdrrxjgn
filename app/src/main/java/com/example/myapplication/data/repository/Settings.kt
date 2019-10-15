package com.example.myapplication.data.repository

import android.content.SharedPreferences
import com.example.myapplication.domain.entity.AuthToken
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val source: SharedPreferences,
    private val gson: Gson
) {
    var authToken: AuthToken?
        get() {
            return try {
                gson.fromJson(source.getString("authToken", ""), AuthToken::class.java)
            } catch (e: JsonSyntaxException) {
                null
            }
        }
        set(value) {
            source.edit()
                .putString("authToken", gson.toJson(value))
                .apply()
        }
}