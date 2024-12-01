package com.example.farmerapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.preferencesOf

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_TYPE = "user_type"
        private const val KEY_USER_NAME = "user_name"
    }

    fun saveUserDetail(userId: String, userType: String, userName: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_ID, userId)
            putString(KEY_USER_TYPE, userType)
            putString(KEY_USER_NAME, userName)
            apply()
        }
    }

    fun isActive(): Boolean {
        return sharedPreferences.getString(KEY_USER_ID, null).isNullOrEmpty().not()
    }

    fun getUserId(): String {
        return sharedPreferences.getString(KEY_USER_ID, null) ?: "-1"
    }

    fun getUserType(): String? {
        return sharedPreferences.getString(KEY_USER_TYPE, null)
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()

    }

}