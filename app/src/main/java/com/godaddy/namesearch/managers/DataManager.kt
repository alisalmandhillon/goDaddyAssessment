package com.godaddy.namesearch.managers

import android.content.Context
import android.content.SharedPreferences
import com.godaddy.namesearch.models.loginModels.LoginResponse
import com.google.gson.GsonBuilder

class DataManager(context: Context) {
     private var preferences: SharedPreferences
     private val preferenceName = "GoDaddyPreferences"
     private val loginKey = "login_response_key"

    init {
        preferences = context.getSharedPreferences(
            preferenceName, Context.MODE_PRIVATE)
    }
    fun saveLoginResponse(loginResponse: LoginResponse) {
         val jsonString = GsonBuilder().create().toJson(loginResponse)
         preferences.edit().putString(loginKey, jsonString).apply()
    }
    fun getLoginResponse(): LoginResponse? {
         val value = preferences.getString(loginKey, null)
        return GsonBuilder().create().fromJson(value, LoginResponse::class.java)
    }

}