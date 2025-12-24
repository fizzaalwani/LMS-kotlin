package com.example.lms.utils


import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("LMS_PREFS", Context.MODE_PRIVATE)

    // Save token
    fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("token", null)
    }

    // Save logged-in user info
    fun saveUser(id: String, name: String, email: String) {
        prefs.edit().apply {
            putString("id", id)
            putString("name", name)
            putString("email", email)
            apply()
        }
    }

    fun getUserId(): String = prefs.getString("id", "") ?: ""
    fun getUserName(): String = prefs.getString("name", "") ?: ""
    fun getUserEmail(): String = prefs.getString("email", "") ?: ""

    fun logout() {
        prefs.edit().clear().apply()
    }
}