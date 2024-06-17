package com.example.jetpack.keycloaklogin.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preferences @Inject constructor(@ApplicationContext context: Context) {
    private val keycloakTokenKey = "KEYCLOAK_TOKEN"
    private val preferences = context.getSharedPreferences("KEYCLOAK_LOGIN_PREFS", Context.MODE_PRIVATE)

    fun getKeycloakToken() = preferences.getString(keycloakTokenKey, null)

    fun setKeycloakToken(token: String?) = preferences.edit().putString(keycloakTokenKey, token).apply()
}