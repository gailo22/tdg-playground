package com.example.jetpack.keycloaklogin.data.repositories

import com.example.jetpack.keycloaklogin.data.local.Preferences
import com.example.jetpack.keycloaklogin.data.remote.KeycloakService
import com.example.jetpack.keycloaklogin.domain.models.KeycloakUser
import com.example.jetpack.keycloaklogin.domain.repositories.KeycloakRepository
import com.example.jetpack.keycloaklogin.data.models.toDomain
import javax.inject.Inject

class KeycloakRepositoryImpl @Inject constructor(
    private val preferences: Preferences,
    private val keycloakService: KeycloakService
): KeycloakRepository {
    override fun addKeycloakToken(token: String) {
        preferences.setKeycloakToken(token)
    }

    override suspend fun getUserInfo(): KeycloakUser {
        return keycloakService.getProfile().toDomain()
    }
}