package com.example.jetpack.keycloaklogin.domain.repositories

import com.example.jetpack.keycloaklogin.domain.models.KeycloakUser

interface KeycloakRepository {

    fun addKeycloakToken(token: String)

    suspend fun getUserInfo(): KeycloakUser

}