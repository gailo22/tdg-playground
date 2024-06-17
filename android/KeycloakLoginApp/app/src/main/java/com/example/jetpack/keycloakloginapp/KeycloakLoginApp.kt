package com.example.jetpack.keycloakloginapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KeycloakLoginApp: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }

    companion object {
        lateinit var instance: KeycloakLoginApp
            private set
    }

}